FROM alpine:3.8

ENV LANG=C.UTF-8

# Here we install GNU libc (aka glibc) and set C.UTF-8 locale as default.

ENV ALPINE_GLIBC_BASE_URL="https://github.com/yangxuan8282/alpine-pkg-glibc/releases/download"
ENV ALPINE_GLIBC_PACKAGE_VERSION="2.27-r0"
ENV ALPINE_GLIBC_BASE_PACKAGE_FILENAME="glibc-$ALPINE_GLIBC_PACKAGE_VERSION.apk"
ENV ALPINE_GLIBC_BIN_PACKAGE_FILENAME="glibc-bin-$ALPINE_GLIBC_PACKAGE_VERSION.apk"
ENV ALPINE_GLIBC_I18N_PACKAGE_FILENAME="glibc-i18n-$ALPINE_GLIBC_PACKAGE_VERSION.apk"
RUN apk add --no-cache --virtual=.build-dependencies wget ca-certificates

RUN wget "$ALPINE_GLIBC_BASE_URL/$ALPINE_GLIBC_PACKAGE_VERSION/$ALPINE_GLIBC_BASE_PACKAGE_FILENAME" \
         "$ALPINE_GLIBC_BASE_URL/$ALPINE_GLIBC_PACKAGE_VERSION/$ALPINE_GLIBC_BIN_PACKAGE_FILENAME"  \
         "$ALPINE_GLIBC_BASE_URL/$ALPINE_GLIBC_PACKAGE_VERSION/$ALPINE_GLIBC_I18N_PACKAGE_FILENAME"
RUN apk add --allow-untrusted --no-cache \
        "$ALPINE_GLIBC_BASE_PACKAGE_FILENAME" \
        "$ALPINE_GLIBC_BIN_PACKAGE_FILENAME" \
        "$ALPINE_GLIBC_I18N_PACKAGE_FILENAME"

RUN /usr/glibc-compat/bin/localedef --force --inputfile POSIX --charmap UTF-8 "$LANG" || true
RUN echo "export LANG=$LANG" > /etc/profile.d/locale.sh
RUN apk del glibc-i18n
RUN rm "/root/.wget-hsts"
RUN apk del .build-dependencies
RUN rm \
        "$ALPINE_GLIBC_BASE_PACKAGE_FILENAME" \
        "$ALPINE_GLIBC_BIN_PACKAGE_FILENAME" \
        "$ALPINE_GLIBC_I18N_PACKAGE_FILENAME"

ENV JAVA_VERSION=8 \
    JAVA_UPDATE=192 \ 
    JAVA_BUILD=12 \ 
    JAVA_PATH=750e1c8617c5452694857ad95c3ee230 \ 
    JAVA_HOME="/usr/lib/jvm/default-jvm" 
    
    
RUN apk add --no-cache --virtual=build-dependencies wget ca-certificates unzip 
RUN wget --header "Cookie: oraclelicense=accept-securebackup-cookie;" "https://download.oracle.com/otn-pub/java/jdk/${JAVA_VERSION}u${JAVA_UPDATE}-b${JAVA_BUILD}/${JAVA_PATH}/jdk-${JAVA_VERSION}u${JAVA_UPDATE}-linux-arm32-vfp-hflt.tar.gz"
RUN tar -xzf "jdk-${JAVA_VERSION}u${JAVA_UPDATE}-linux-arm32-vfp-hflt.tar.gz"
RUN mkdir -p "/usr/lib/jvm"
RUN mv "/tmp/jdk1.${JAVA_VERSION}.0_${JAVA_UPDATE}" "/usr/lib/jvm/java-${JAVA_VERSION}-oracle"
RUN ln -s "java-${JAVA_VERSION}-oracle" "$JAVA_HOME"
RUN ln -s "$JAVA_HOME/bin/"* "/usr/bin/" && \
RUN rm -rf "$JAVA_HOME/"*src.zip && \
RUN wget --header "Cookie: oraclelicense=accept-securebackup-cookie;" \
        "http://download.oracle.com/otn-pub/java/jce/${JAVA_VERSION}/jce_policy-${JAVA_VERSION}.zip" && \
RUN unzip -jo -d "${JAVA_HOME}/jre/lib/security" "jce_policy-${JAVA_VERSION}.zip" && \
RUN rm "${JAVA_HOME}/jre/lib/security/README.txt" && \
RUN apk del build-dependencies && \
RUN rm "/tmp/"* && \
    \
RUN echo 'public class Main { public static void main(String[] args) { System.out.println("Java code is running fine!"); } }' > Main.java && \
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

