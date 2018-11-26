node('docker') {
    stage('Checkout') {
        checkout scm
    }

    stage('Build Master') {
        def customImage = docker.build("shocki/jenkins-master", "./main/Dockerfile")
        customImage.push()
    }

    stage('Build Agent') {
        def customImage = docker.build("shocki/jenkins-agent", "./agent/Dockerfile")
        customImage.push()
    }
}