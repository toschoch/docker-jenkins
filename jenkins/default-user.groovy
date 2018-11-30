import jenkins.model.*
import hudson.security.*

def env = System.getenv()

def userName = env.JENKINS_USER
def userPass = env.JENKINS_PASS

def userNameFile = new File(env.SECRETS_DIR + "/" + userName)
def userPassFile = new File(env.SECRETS_DIR + "/" + userPass)

if (userNameFile.exists())
{
    userName = userNameFile.text.trim()
}
if (userPassFile.exists())
{
    userPass = userPassFile.text.trim()
}


def jenkins = Jenkins.getInstance()
jenkins.setSecurityRealm(new HudsonPrivateSecurityRealm(false))
jenkins.setAuthorizationStrategy(new GlobalMatrixAuthorizationStrategy())

def user = jenkins.getSecurityRealm().createAccount(userName, userPass)
user.save()

jenkins.getAuthorizationStrategy().add(Jenkins.ADMINISTER, userName)
jenkins.save()
