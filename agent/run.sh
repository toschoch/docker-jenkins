#! /bin/bash -e

echo "looking for secrets..."
echo "... /run/secrets/${JENKINS_USER}"
if [ -f "/run/secrets/${JENKINS_USER}" ]; then
    echo "found '${JENKINS_USER}' secret!"
    read JENKINS_USER < /run/secrets/${JENKINS_USER}
    export JENKINS_USER 
else
    echo "secret '${JENKINS_USER}' not found!"
fi

echo "... /run/secrets/${JENKINS_PASS}"
if [ -f "/run/secrets/${JENKINS_PASS}" ]; then
    echo "found '${JENKINS_PASS}' secret!"
    read JENKINS_PASS < /run/secrets/${JENKINS_PASS}
    export JENKINS_PASS
else
    echo "secret '${JENKINS_PASS}' not found!"
fi

# GROUP=$(stat -c %G /var/run/docker.sock)
# if [ ! $(grep ^$GROUP: /etc/group) ]; then
#     addgroup -G $(stat -c %g /var/run/docker.sock) $GROUP
# fi
# adduser jenkins $GROUP

# As argument is not jenkins, assume user want to run his own process, for example a `bash` shell to explore this image
exec "$@"
