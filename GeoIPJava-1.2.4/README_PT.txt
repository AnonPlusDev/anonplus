Para começar, execute

cd source/com/maxmind/geoip/
javac *. java
cd -
CLASSPATH = ":. Source" javac *. java
CLASSPATH = ":. Source" java CountryLookupTest

Por padrão, CountryLookupTest espera encontrar o
GeoIP banco de dados País em /usr/local/share/GeoIP/GeoIP.dat

Alterar API IMPORTANTE para os usuários 1.1.x - a partir de GeoIP 1.1.0 do
métodos lookupCountryXxxx retornar nulo se um país não pode
ser encontrada (é usado para retornar '-' ou 'N / A' Certifique-se de verificar o.
retornar valor para nulo!

Esta é a versão 1.2.3 da interface Java para GeoIP. Para mais informações
ver http://www.maxmind.com/

A partir da versão 1.1.4 esta API é totalmente isento de segmentos.

Para começar a GeoIP País, olhe para o código em CountryLookupTest.java.

CityLookupTest.java contém um exemplo para o banco de dados Cidade MaxMind GeoIP.
OrgLookupTest.java contém exemplos para a Organização e bancos de dados GeoIP ISP.
RegionLookupTest.java contém um exemplo para o banco de dados de GeoIP MaxMind Região.
NetspeedLookupTest.java contém um exemplo para o banco de dados MaxMind Netspeed GeoIP.

BenchmarkGeoIP.java contém referências para várias bases de dados usando várias opções.

Note-se que CountryLookupTest pressupõe que você tenha o banco de dados GeoIP País
instalado em /usr/local/share/GeoIP/GeoIP.dat

Um banco de dados livre País GeoLite está disponível em:
http://www.maxmind.com/app/geoip_country

Um banco de dados Cidade GeoLite livre está disponível em:
http://www.maxmind.com/app/geolitecity

MaxMind País GeoIP oferece maior precisão sobre o banco de dados livre.
MaxMind GeoIP País, Região, Cidade, ISP, e Organização estão disponíveis
para a compra a partir daqui:
http://www.maxmind.com/app/products

Envie os seus comentários para support@maxmind.com

Para gerar o arquivo JAR cd, para o diretório de origem e executar

jar cf maxmindgeoip.jar com/maxmind/geoip/Country.class com/maxmind/geoip/DatabaseInfo.class com/maxmind/geoip/Location.class com/maxmind/geoip/LookupService.class com/maxmind/geoip/Region.class


Cache de memória e outras opções
================================
As seguintes opções podem ser passado como o segundo parâmetro para o
LookupService construtor:

GEOIP_STANDARD - banco de dados lidos a partir do sistema de arquivos, usa menos memória.

GEOIP_MEMORY_CACHE - banco de dados de carregar na memória, desempenho mais rápido
        mas usa mais memória

GEOIP_CHECK_CACHE - check para banco de dados atualizado. Se banco de dados foi atualizado,
        reload filehandle e / ou cache de memória.

GEOIP_INDEX_CACHE - cache apenas
        a porção índice mais freqüentemente acessados ??do banco de dados, resultando
        em pesquisas mais rápido do que GEOIP_STANDARD, mas menor uso de memória do que
        GEOIP_MEMORY_CACHE - útil para bancos de dados maiores, como
        GeoIP Organização e GeoIP City. Note, por País GeoIP Região,
        e bancos de dados Netspeed, GEOIP_INDEX_CACHE é equivalente a GEOIP_MEMORY_CACHE

Note que as opções podem ser combinadas, por exemplo:
LookupService cl = new LookupService (dbfile, LookupService.GEOIP_MEMORY_CACHE | LookupService.GEOIP_CHECK_CACHE);

Notas do Windows
========================
Se ele não funciona no Windows, tente remover ou comentar todos os "pacote com.maxmind.geoip" linhas
de todos os arquivos.