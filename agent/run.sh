#! /bin/ash -e

echo "looking for secrets..."
if [ -f "/run/secrets/${JENKINS_USER}" ]; then
    echo "found '${JENKINS_USER}' secret!"
    read USR < /run/secrets/${JENKINS_USER}    
else
    USR=${JENKINS_USER}
fi
COMMAND_OPTIONS="${COMMAND_OPTIONS} -username $USR"

if [ -f "/run/secrets/${JENKINS_PASS}" ]; then
    echo "found '${JENKINS_PASS}' secret!"
    read PSS < /run/secrets/${JENKINS_PASS}
    export PSS
else
    PSS=${JENKINS_PASS}
    export PSS
fi
COMMAND_OPTIONS="${COMMAND_OPTIONS} -passwordEnvVariable PSS"
COMMAND_OPTIONS="${COMMAND_OPTIONS} -name $(hostname) -disableClientsUniqueId"

# GROUP=$(stat -c %G /var/run/docker.sock)
# if [ ! $(grep ^$GROUP: /etc/group) ]; then
#     addgroup -G $(stat -c %g /var/run/docker.sock) $GROUP
# fi
# adduser jenkins $GROUP

# exec su jenkins -c "exec java -jar /home/jenkins/swarm-client-${SWARM_CLIENT_VERSION}.jar ${COMMAND_OPTIONS}"
java -jar /home/jenkins/swarm-client-${SWARM_CLIENT_VERSION}.jar ${COMMAND_OPTIONS}
