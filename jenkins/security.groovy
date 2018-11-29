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
    def user = userFile.text.trim()
}
else
{
    def user = env.JENKINS_USER
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

def hudsonRealm = new HudsonPrivateSecurityRealm(false)
hudsonRealm.createAccount(user, pass)
instance.setSecurityRealm(hudsonRealm)

def strategy = new FullControlOnceLoggedInAuthorizationStrategy()
instance.setAuthorizationStrategy(strategy)
instance.save()

Jenkins.instance.getInjector().getInstance(AdminWhitelistRule.class).setMasterKillSwitch(false)

println "User " + user + " was created"
