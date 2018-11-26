Jenkins Build Server Setup for Docker Swarm
===============================
author: Tobias Schoch

Overview
--------

A docker stack containing a ready to go configuration for a jenkins build server master/slaves for a running docker swarm.


Change-Log
----------
##### 0.0.1
* initial version


Installation / Usage
--------------------
clone the repo:

```bash
git clone https://github.com/toschoch/docker-jenkins.git
```

create the docker secrets
```bash
echo "my-jenkins-user" | docker secret create jenkins-user --
echo "my-jenkins-pw" | docker secret create jenkins-pass --
```

deploy the stack
```bash
docker stack deploy -c docker-compose.yml jenkins
```