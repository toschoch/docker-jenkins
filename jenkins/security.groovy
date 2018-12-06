#!groovy

import jenkins.model.*
import hudson.security.*
import jenkins.security.s2m.AdminWhitelistRule

def instance = Jenkins.getInstance()
def env = System.getenv()

def userFile = new File(env.SECRETS_DIR + "/" + env.JENKINS_USER)
def userPass = new File(env.SECRETS_DIR + "/" + env.JENKINS_PASS)

if (userFile.exists())
{
    def userName = userFile.text.trim()
}
else
{
    def userName = env.JENKINS_USER
}

if (userPass.exists())
{
    def pass = userPass.text.trim()
}
else
{
    def pass = env.JENKINS_PASS
}


println "Creating user " + user + "..."

instance.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
instance.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())

def user = jenkins.getSecurityRealm().createAccount(userName, pass)
user.save()

instance.getAuthorizationStrategy().add(Jenkins.ADMINISTER, userName)
instance.save()

println "User " + user + " was created"
