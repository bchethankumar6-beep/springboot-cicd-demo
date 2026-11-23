
pipeline {
    agent any

    environment {
        MAVEN = 'C:\\Program Files\\Apache\\apache-maven-3.10.1\\bin\\mvn.cmd'
        DOCKER = 'C:\\Users\\ADMIN\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe'
        IMAGE_NAME = 'springboot-cicd-demo'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                bat "\"${MAVEN}\" -B clean verify"
            }
        }

        stage('Build Docker Image') {
            steps {
                bat "\"${DOCKER}\" build -t ${IMAGE_NAME}:${BUILD_NUMBER} ."
            }
        }

        stage('Validate Docker Image') {
            steps {
                bat "\"${DOCKER}\" image inspect ${IMAGE_NAME}:${BUILD_NUMBER}"
            }
        }
    }

    post {
        success {
            echo 'CI Pipeline completed successfully!'
        }
        failure {
            echo 'CI Pipeline failed. Check the console output.'
        }
    }
}
