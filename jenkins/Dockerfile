
FROM frolvlad/alpine-glibc:alpine-3.8 

ENV JAVA_VERSION=8 \
    JAVA_UPDATE=192 \ 
    JAVA_BUILD=12 \ 
    JAVA_PATH=2787e4a523244c269598db4e85c51e0c \ 
    JAVA_HOME="/usr/lib/jvm/default-jvm" 
    
    
RUN apk add --no-cache --virtual=build-dependencies wget ca-certificates unzip && \
    cd "/tmp" && \
    wget --header "Cookie: oraclelicense=accept-securebackup-cookie;" \
        "http://download.oracle.com/otn-pub/java/jdk/${JAVA_VERSION}u${JAVA_UPDATE}-b${JAVA_BUILD}/${JAVA_PATH}/jdk-${JAVA_VERSION}u${JAVA_UPDATE}-linux-arm32-vfp-hflt.tar.gz" && \
    tar -xzf "jdk-${JAVA_VERSION}u${JAVA_UPDATE}-linux-arm32-vfp-hflt.tar.gz" && \
    mkdir -p "/usr/lib/jvm" && \
    mv "/tmp/jdk1.${JAVA_VERSION}.0_${JAVA_UPDATE}" "/usr/lib/jvm/java-${JAVA_VERSION}-oracle" && \
    ln -s "java-${JAVA_VERSION}-oracle" "$JAVA_HOME" && \
    ln -s "$JAVA_HOME/bin/"* "/usr/bin/" && \
    rm -rf "$JAVA_HOME/"*src.zip && \
    wget --header "Cookie: oraclelicense=accept-securebackup-cookie;" \
        "http://download.oracle.com/otn-pub/java/jce/${JAVA_VERSION}/jce_policy-${JAVA_VERSION}.zip" && \
    unzip -jo -d "${JAVA_HOME}/jre/lib/security" "jce_policy-${JAVA_VERSION}.zip" && \
    rm "${JAVA_HOME}/jre/lib/security/README.txt" && \
    apk del build-dependencies && \
    rm "/tmp/"* && \
    \
    echo 'public class Main { public static void main(String[] args) { System.out.println("Java code is running fine!"); } }' > Main.java && \
    javac Main.java && \
    java Main && \
    rm -r "/tmp/"*

# FROM jenkins/jenkins:lts-alpine

# ENV SECRETS_DIR=/run/secrets
# # Whether to skip setup wizard
# ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

# # Setting up environment variables for Jenkins admin user
# ENV JENKINS_USER jenkins-user
# ENV JENKINS_PASS jenkins-pass

# # install docker for executors
# USER root
# RUN apk add --no-cache docker git

# # Copy the modified install-plugins.sh that does not install all standard plugins
# #COPY install-plugins.sh /usr/local/bin/install-plugins.sh
# #RUN chmod 755 /usr/local/bin/install-plugins.sh

# USER jenkins

# # Creates username and password specified through environment variables JENKINS_USER_SECRET and JENKINS_PASS_SECRET
# COPY default-user.groovy /usr/share/jenkins/ref/init.groovy.d/default-user.groovy


# # Install a list of plugins from the file 'plugins.txt' and their dependencies
# COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
# RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

