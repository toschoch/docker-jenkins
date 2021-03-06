def axisNode = ["Linux (arm)","Linux (x64)"]
def imageTags = ["arm32v6","amd64"]
def manifestAnnotations = [["variant":"armv7l"],[:]]
def imageNames = []
def tasks = [:]

stage("Checkout") {
    node {
        echo "checkout..."
        checkout scm
        env.VERSION = gitVersion()
        echo "Version = ${env.VERSION}"
        env.ISTAG = isTag()
        echo "Tag = ${env.ISTAG}"
    }
}

for(int i=0; i< axisNode.size(); i++) {
    def axisNodeValue = axisNode[i]
    def archTag = imageTags[i]
    withCredentials([usernamePassword(credentialsId: 'docker public', 
                                      usernameVariable: 'USERNAME')]) {
        def imageTag = env.ISTAG ? "jenkins:${archTag}-${env.VERSION}" : "jenkins:${archTag}"
        imageTag = "${USERNAME}/${imageTag}"
    }
    tasks[axisNodeValue] = {
        node(axisNodeValue && 'docker') {
            println "Node=${env.NODE_NAME}"
            println "Arch=${archTag}"
            
            println "build image..."
            def builtImage = docker.build(imageTag,'./jenkins')

            println "push to registry..."
            docker.withRegistry('','docker public') {
                builtImage.push()
            }
            imageNames.plus(imageTag)
        }
    }
}

stage ("Build") {
    tasks
}

stage("Push Manifest") {
    node("docker" && "experimental") {
        withCredentials([usernamePassword(credentialsId: 'docker public', 
                                      usernameVariable: 'USERNAME')]) {
            def imageTag = env.ISTAG ? "jenkins:${env.VERSION}" : "jenkins:latest"
            imageTag = "${USERNAME}/${imageTag}"
        }

        println "create manifest..."
        def tagList = ""
        for(int i=0; i< imageNames.size(); i++) {
            tagList = "${tagList} ${imageNames[i]}"
        }
        sh "docker manifest create ${imageTag} ${tagList}"

        println "annotate images..."
        for(int i=0; i< imageNames.size(); i++) {
            def cmd = "docker manifest annotate "
            for (e in manifestAnnotations[i]) {
                cmd = cmd + "--" + e.key + " " + e.value
            }
            cmd = cmd + " " + imageNames[i]
            sh cmd
        }

        sh "docker manifest push ${imageTag}"
    }
}


def getCommit() {
    return sh(script: 'git rev-parse HEAD', returnStdout: true)?.trim()
}

def gitVersion() {
    desc = sh(script: "git describe --tags --long --dirty", returnStdout: true)?.trim()
    parts = desc.split('-')
    assert parts.size() in [3, 4]
    dirty = (parts.size() == 4)
    tag = parts[0]
    count = parts[1]
    sha = parts[2]
    if (count == '0' && !dirty) {
        return tag
    }
    return sprintf( '%1$s.dev%2$s+%3$s', [tag, count, sha.substring(1)])
}

def isTag() {
    commit = getCommit()
    if (commit) {
        desc = sh(script: "git describe --tags --long ${commit}", returnStdout: true)?.trim()
        match = desc =~ /.+-[0-9]+-g[0-9A-Fa-f]{6,}$/
        result = !match
        match = null
        return result
    }
    return false
}
