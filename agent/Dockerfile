FROM openjdk:8-jre-alpine

ENV SWARM_CLIENT_VERSION="3.9" \
    DOCKER_COMPOSE_VERSION="1.23.1" \
    COMMAND_OPTIONS="" \
    USER_NAME_SECRET="" \
    PASSWORD_SECRET=""

RUN adduser -G root -D jenkins && \
    apk --update --no-cache add docker python py-pip git openssh ca-certificates openssl bash && \
    wget -q https://repo.jenkins-ci.org/releases/org/jenkins-ci/plugins/swarm-client/${SWARM_CLIENT_VERSION}/swarm-client-${SWARM_CLIENT_VERSION}.jar -P /home/jenkins/ && \
    pip install docker-compose jenkins-webapi 

# Start-up script to attach the slave to the master
ADD slave.py /var/lib/jenkins/slave.py
 
WORKDIR /home/jenkins
 
ENV JENKINS_URL "http://jenkins"
ENV JENKINS_SLAVE_ADDRESS ""
ENV JENKINS_USER "jenkins-user"
ENV JENKINS_PASS "jenkins-pass"
ENV SLAVE_NAME ""
ENV SLAVE_SECRET ""
ENV SLAVE_EXECUTORS "1"
ENV SLAVE_LABELS "docker"
ENV SLAVE_WORING_DIR ""
ENV CLEAN_WORKING_DIR "true"


COPY run.sh /var/lib/jenkins

ENTRYPOINT ["/var/lib/jenkins/run.sh"]

CMD [ "python", "-u", "/var/lib/jenkins/slave.py" ]

