def app

node {
    stage('Checkout') {
        checkout scm 
    }

    stage('Ready') {      
        echo 'Ready to build'
        gradleHome = tool 'gradle' 
    }

    stage('Build') {
        sh "${gradleHome}/bin/gradle clean build"
    }

    stage('Build image') {
        app = docker.build('uahannam/member-service')
    }

    stage('Push image') {
        docker.withRegistry('https://192.168.45.205', 'harbor') {
            app.push("latest")
        }
    }
}
