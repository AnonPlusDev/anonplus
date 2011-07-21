Para come�ar, execute

cd source/com/maxmind/geoip/
javac *. java
cd -
CLASSPATH = ":. Source" javac *. java
CLASSPATH = ":. Source" java CountryLookupTest

Por padr�o, CountryLookupTest espera encontrar o
GeoIP banco de dados Pa�s em /usr/local/share/GeoIP/GeoIP.dat

Alterar API IMPORTANTE para os usu�rios 1.1.x - a partir de GeoIP 1.1.0 do
m�todos lookupCountryXxxx retornar nulo se um pa�s n�o pode
ser encontrada (� usado para retornar '-' ou 'N / A' Certifique-se de verificar o.
retornar valor para nulo!

Esta � a vers�o 1.2.3 da interface Java para GeoIP. Para mais informa��es
ver http://www.maxmind.com/

A partir da vers�o 1.1.4 esta API � totalmente isento de segmentos.

Para come�ar a GeoIP Pa�s, olhe para o c�digo em CountryLookupTest.java.

CityLookupTest.java cont�m um exemplo para o banco de dados Cidade MaxMind GeoIP.
OrgLookupTest.java cont�m exemplos para a Organiza��o e bancos de dados GeoIP ISP.
RegionLookupTest.java cont�m um exemplo para o banco de dados de GeoIP MaxMind Regi�o.
NetspeedLookupTest.java cont�m um exemplo para o banco de dados MaxMind Netspeed GeoIP.

BenchmarkGeoIP.java cont�m refer�ncias para v�rias bases de dados usando v�rias op��es.

Note-se que CountryLookupTest pressup�e que voc� tenha o banco de dados GeoIP Pa�s
instalado em /usr/local/share/GeoIP/GeoIP.dat

Um banco de dados livre Pa�s GeoLite est� dispon�vel em:
http://www.maxmind.com/app/geoip_country

Um banco de dados Cidade GeoLite livre est� dispon�vel em:
http://www.maxmind.com/app/geolitecity

MaxMind Pa�s GeoIP oferece maior precis�o sobre o banco de dados livre.
MaxMind GeoIP Pa�s, Regi�o, Cidade, ISP, e Organiza��o est�o dispon�veis
para a compra a partir daqui:
http://www.maxmind.com/app/products

Envie os seus coment�rios para support@maxmind.com

Para gerar o arquivo JAR cd, para o diret�rio de origem e executar

jar cf maxmindgeoip.jar com/maxmind/geoip/Country.class com/maxmind/geoip/DatabaseInfo.class com/maxmind/geoip/Location.class com/maxmind/geoip/LookupService.class com/maxmind/geoip/Region.class


Cache de mem�ria e outras op��es
================================
As seguintes op��es podem ser passado como o segundo par�metro para o
LookupService construtor:

GEOIP_STANDARD - banco de dados lidos a partir do sistema de arquivos, usa menos mem�ria.

GEOIP_MEMORY_CACHE - banco de dados de carregar na mem�ria, desempenho mais r�pido
        mas usa mais mem�ria

GEOIP_CHECK_CACHE - check para banco de dados atualizado. Se banco de dados foi atualizado,
        reload filehandle e / ou cache de mem�ria.

GEOIP_INDEX_CACHE - cache apenas
        a por��o �ndice mais freq�entemente acessados ??do banco de dados, resultando
        em pesquisas mais r�pido do que GEOIP_STANDARD, mas menor uso de mem�ria do que
        GEOIP_MEMORY_CACHE - �til para bancos de dados maiores, como
        GeoIP Organiza��o e GeoIP City. Note, por Pa�s GeoIP Regi�o,
        e bancos de dados Netspeed, GEOIP_INDEX_CACHE � equivalente a GEOIP_MEMORY_CACHE

Note que as op��es podem ser combinadas, por exemplo:
LookupService cl = new LookupService (dbfile, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);

Notas do Windows
========================
Se ele n�o funciona no Windows, tente remover ou comentar todos os "pacote com.maxmind.geoip" linhas
de todos os arquivos.