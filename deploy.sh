echo "stop tomcat"
service tomcat stop

rm -r /usr/share/tomcat/webapps/jocwk
rm /usr/share/tomcat/webapps/jocwk.war

cp /home/kesslewd/git/other/jocwk/dist/jocwk.war /usr/share/tomcat/webapps/

echo "start tomcat"
service tomcat start
