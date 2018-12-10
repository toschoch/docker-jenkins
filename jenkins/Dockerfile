FROM shocki/docker-alpine-oracle-jdk-arm

ARG user=jenkins
ARG group=jenkins
ARG uid=1000
ARG gid=1000
ARG http_port=8080
ARG agent_port=50000

ENV JENKINS_HOME=/var/jenkins_home \
    JENKINS_SLAVE=${agent_port} \
    JENKINS_UC=https://updates.jenkins.io \
    TINI_VERSION=0.18.0 \
    TINI_SHA=efc2933bac3290aae1180a708f58035baf9f779833c2ea98fcce0ecdab68aa61 \
    JENKINS_VERSION=2.154 \
    JENKINS_SHA=2d12418c2e482eaf2cf1d13ad2b25f58e801b80c46cbd6dd3bae4c6df308d98e \
    SECRETS_DIR=/run/secrets \
    JAVA_OPTS="-Djenkins.install.runSetupWizard=false" \
    JENKINS_USER=jenkins-user \
    JENKINS_PASS=jenkins-pass

# Jenkins home directory is a volume, so configuration and build history 
# can be persisted and survive image upgrades
RUN  mkdir -p /usr/share/jenkins/ref/init.groovy.d

COPY init.groovy /usr/share/jenkins/ref/init.groovy.d/tcp-slave-agent-port.groovy
COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/default-user.groovy

# for main web interface:
EXPOSE ${http_port}

# will be used by attached slave agents:
EXPOSE ${agent_port}

ENV JENKINS_URL=https://updates.jenkins-ci.org/download/war/${JENKINS_VERSION}/jenkins.war

COPY jenkins-support /usr/local/bin/jenkins-support
COPY jenkins.sh /usr/local/bin/jenkins.sh
COPY install-plugins.sh /usr/local/bin/install-plugins.sh
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt

RUN apk add --no-cache git openssh-client curl unzip bash ttf-dejavu coreutils docker && \
    addgroup -g ${gid} ${group} && \
    adduser -h "$JENKINS_HOME" -u ${uid} -G ${group} -s /bin/bash -D ${user} && \
    curl -fsSL https://github.com/krallin/tini/releases/download/v${TINI_VERSION}/tini-static-armhf -o /bin/tini && chmod +x /bin/tini && \
    echo "$TINI_SHA  /bin/tini" | sha256sum -c - && \
    curl -fsSL ${JENKINS_URL} -o /usr/share/jenkins/jenkins.war && \ 
    echo "${JENKINS_SHA}  /usr/share/jenkins/jenkins.war" | sha256sum -c - && \
    chown -R ${uid} "$JENKINS_HOME" /usr/share/jenkins/ref && \
    /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

VOLUME $JENKINS_HOME

ENV COPY_REFERENCE_FILE_LOG=$JENKINS_HOME/copy_reference_file.log

USER ${user}

ENTRYPOINT ["/bin/tini", "--", "/usr/local/bin/jenkins.sh"]


