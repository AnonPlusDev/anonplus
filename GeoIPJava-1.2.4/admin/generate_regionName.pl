#!/usr/bin/perl

# Used to generate regionName.java
# usage: ./generate_regionName.pl > ../source/com/maxmind/geoip/regionName.java

use strict;
use warnings;

print <<__JAVA_CODE__;
package com.maxmind.geoip;
// generated automatically from admin/generate_regionName.pl
public class regionName {
static public String regionNameByCode(String country_code,String region_code) {
    String name = null;
    int region_code2 = -1;
    if (region_code == null) { return null; }
    if (region_code.equals("")) { return null; }

    if (    ((region_code.charAt(0) >= 48 ) && ( region_code.charAt(0) < ( 48 + 10 )))
         && ((region_code.charAt(1) >= 48 ) && ( region_code.charAt(1) < ( 48 + 10 )))
    ){
      // only numbers, that shorten the large switch statements
      region_code2 = (region_code.charAt(0)- 48) * 10 + region_code.charAt(1) - 48;
    }
	  else if (    (    ((region_code.charAt(0) >= 65) && (region_code.charAt(0) < (65 + 26)))
                 || ((region_code.charAt(0) >= 48) && (region_code.charAt(0) < (48 + 10))))
            && (    ((region_code.charAt(1) >= 65) && (region_code.charAt(1) < (65 + 26)))
                 || ((region_code.charAt(1) >= 48) && (region_code.charAt(1) < (48 + 10))))
  ) {

    region_code2 = (region_code.charAt(0) - 48) * (65 + 26 - 48) + region_code.charAt(1) - 48 + 100;
  }

  if (region_code2 == -1) {return null;}
__JAVA_CODE__

# iso3166_2.txt extracted from http://www.maxmind.com/app/iso3166_2
open( FILE, "iso3166_2.txt" ) or die $!;
<FILE>;
my $last_country_code = "";
while ( my $str = <FILE> ) {
  chomp($str);
  my ( $country_code, $region_code, $name ) = split( ",", $str );
  $region_code =~ /^[A-Z]{2}$/ or die "Wrong region code";
  my $region_code2 =
    ( ( ord( substr( $region_code, 0, 1 ) ) - 48 ) * ( 65 + 26 - 48 ) ) +
    ord( substr( $region_code, 1, 1 ) ) - 48 + 100;
  readcode( $last_country_code, $country_code, $region_code, $region_code2,
            $name );
  $last_country_code = $country_code;
}
close(FILE);
print "      }\n";
print "    }\n";

# fips10_4.txt extracted from http://www.maxmind.com/app/fips10_4
open( FILE, "fips10_4.txt" ) or die $!;
<FILE>;
$last_country_code = "";
while ( my $str = <FILE> ) {
  chomp($str);
  my ( $country_code, $region_code, $name ) = split( ",", $str );
  next if ( $country_code eq "US" );
  next if ( $country_code eq "CA" );
  my $region_code2;
  if ( $region_code =~ /^\d\d$/ ) {
    $region_code2 =
      ( ord( substr( $region_code, 0, 1 ) ) - 48 ) * 10 +
      ord( substr( $region_code, 1, 1 ) ) - 48;

  }
  elsif ( $region_code =~ /^[A-Z0-9]{2}$/ ) {
    $region_code2 =
      ( ( ord( substr( $region_code, 0, 1 ) ) - 48 ) * ( 65 + 26 - 48 ) ) +
      ord( substr( $region_code, 1, 1 ) ) - 48 + 100;
  }
  else {
    die "Region code seems wrong $region_code\n";
  }
  readcode( $last_country_code, $country_code, $region_code, $region_code2,
            $name );
  $last_country_code = $country_code;
}
print "      }\n";
print "    }\n";
print "    return name;\n";
print "  }\n";
print "}\n";

close(FILE);

sub readcode {
  my ( $last_country_code, $country_code, $region_code, $region_code2, $name ) =
    @_;
  if ( $country_code ne $last_country_code ) {
    if ( $last_country_code ne "" ) {
      print "      }\n";
      print "    }\n";
    }
    print "    if (country_code.equals(" . qq(")
      . $country_code . qq(")
      . ") == true) {\n";
    print "      switch (region_code2) {\n";
  }

  #  $name =~ s!\s+!!g;
  $name =~ s!\"!!g;
  $name = qq(") . $name . qq(");
  print "        case " . $region_code2 . ":\n";
  print "        name = " . $name . ";\n";
  print "        break;\n";
}

