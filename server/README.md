TODO List:
- IMPORTANT: Add install scripts for DB.
- If you want to continue developing - JUnit tests! Integration tests! 

1) Make sure if spring.datasource.url is good after ? symbol. I guess here is better solutions.
2) Add integration test for mongo and mysql. Run it before every running.
3) Test user roles during creating new mysqlBook for mongo repository.
4) Change true/false in services. Return code statuses for front-end.
5) Night testing. UserDetails <> KeyCloak user. Is everybody exist and every uuid = uuidUser?
6) Add mappers for all entities.
7) Change all nulls to Collection.emptyList() etc.
8) Add authorization for /dictionary, because everyone will use it.
9) Use example for API request from Oxford dictionary on swagger page.
10) Fixed versions of gradle imports.
11) Logs
12) Counter of words for demo page.
13) keycloak-client.json for auth.

How to install and run project?

1) Run Keycloak on http://localhost:8180/auth/ and configure it. You should import keycloak configuration from application resources.
2) Run MongoDB and create there dictionarydb // WILL BE DEPRECATED
3) Run Postgres
4) Mkdirs for /library/
5) Mkdirs for /external-library/
6) Run application.



docker exec dictionaryapp_dictionary_app_prod_keycloak_1 /opt/jboss/keycloak/bin/kcadm.sh  config credentials --server http://localhost:8080/auth --realm master --user test --password test

docker exec dictionaryapp_dictionary_app_prod_keycloak_1 /opt/jboss/keycloak/bin/kcadm.sh update realms/master -s sslRequired=NONE

default roles of user you can configure in keycloak roles.

docker delete everything: docker system prune -a --volumes
docker delete everything: docker rmi $(docker images -a -q)


Clear Iptables:
iptables -P INPUT ACCEPT
iptables -P FORWARD ACCEPT
iptables -P OUTPUT ACCEPT
iptables -t nat -F
iptables -t mangle -F
iptables -F
iptables -X

Add port 80: 
sudo iptables -S
sudo iptables -A INPUT -p tcp --dport 80 -m conntrack --ctstate NEW,ESTABLISHED -j ACCEPT
sudo iptables -A OUTPUT -p tcp --sport 80 -m conntrack --ctstate ESTABLISHED -j ACCEPT