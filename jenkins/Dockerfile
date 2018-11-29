
FROM jenkins/jenkins:lts-alpine

ENV SECRETS_DIR=/run/secrets
# Whether to skip setup wizard
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# Setting up environment variables for Jenkins admin user
ENV JENKINS_USER jenkins-user
ENV JENKINS_PASS jenkins-pass

# install docker for executors
USER root
RUN apk add --no-cache docker
USER jenkins

# Creates username and password specified through environment variables JENKINS_USER_SECRET and JENKINS_PASS_SECRET
COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy

# Install a list of plugins from the file 'plugins.txt' and their dependencies
COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

