# CREATE H2 DATABASE
C:\Users\Internet\Downloads\jdk-11.0.4\bin\java.exe -cp C:\Users\Internet\.m2\repository\com\h2database\h2\1.4.199\h2-1.4.199.jar org.h2.tools.Shell

# START H2 DATABASE
C:\Users\Internet\Downloads\jdk-11.0.4\bin\java.exe -cp C:\Users\Internet\.m2\repository\com\h2database\h2\1.4.199\h2-1.4.199.jar org.h2.tools.Server -tcp -baseDir .


# CREATE POSTGRES DATABASE
cd D:\TAF\pgsql\bin
initdb.exe -D D:\TAF\pgsql\data
pg_ctl -D ^"D^:^\TAF^\pgsql^\data^" -l fichier_de_trace start

# START POSTGRES DATABASE
.\pg_ctl.exe -D "D:\TAF\pgsql\data" -l fichier_de_trace start
