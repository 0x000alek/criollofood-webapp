## CriolloFood WebApplication
#### JAVA_HOME y JDK
Asegurate de que la variable de entorno **JAVA_HOME** esté correctamente configurada apuntando a la carpeta de instalación
del JDK. Se requiere tener instalado **JDK 11** para poder ejecutar el proyecto.
##### Windows
```
echo %JAVA_HOME% 
```
##### Unix (MacOSX, Linux)
```
echo $JAVA_HOME
```

#### JDBC Driver


#### Archivo properties
Se requiere modificar el archivo de propiedades **application.properties** conforme al siguiente contenido:
```
oracle.criollofood.datasource.url=jdbc:oracle:thin:@dbcf_high
oracle.criollofood.datasource.username=admin
oracle.criollofood.datasource.password=DuocUC..2020
oracle.criollofood.datasource.driver-class-name=oracle.jdbc.OracleDriver
oracle.criollofood.datasource.wallet-location=../config/Wallet_DBCF
```
