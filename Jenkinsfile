node('docker') {
    stage('Checkout') {
        checkout scm
    }

    stage('Build Master') {
        sh 'cd main/'
        def customImage = docker.build("shocki/jenkins-master")
        customImage.push()
        sh 'cd ..'
    }

    stage('Build Agent') {
        sh 'cd agent/'
        def customImage = docker.build("shocki/jenkins-master")
        customImage.push()
        sh 'cd ..'
    }
}