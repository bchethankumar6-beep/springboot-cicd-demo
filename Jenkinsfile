
pipeline {
    agent any

    environment {
        MAVEN = 'C:\\Program Files\\Apache\\apache-maven-3.10.1\\bin\\mvn.cmd'
        DOCKER = 'C:\\Users\\ADMIN\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\docker.exe'
        KUBECTL = 'C:\\Users\\ADMIN\\AppData\\Local\\Programs\\DockerDesktop\\resources\\bin\\kubectl.exe'
        KUBECONFIG = 'C:\\Windows\\System32\\config\\systemprofile\\.kube\\config'
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

       stage('Push to Docker Hub') {
    steps {
        withCredentials([string(
            credentialsId: 'dockerhub-token-secret',
            variable: 'DOCKERHUB_TOKEN'
        )]) {
            bat '''
                echo %DOCKERHUB_TOKEN% | "%DOCKER%" login -u "chethan123444" --password-stdin
                if errorlevel 1 exit /b 1

                "%DOCKER%" tag "%IMAGE_NAME%:%BUILD_NUMBER%" "chethan123444/%IMAGE_NAME%:%BUILD_NUMBER%"
                if errorlevel 1 exit /b 1

                "%DOCKER%" push "chethan123444/%IMAGE_NAME%:%BUILD_NUMBER%"
                if errorlevel 1 exit /b 1

                "%DOCKER%" logout
            '''
        }
    }
}

       stage('Deploy to Kubernetes') {
    steps {
        bat '''
            "%KUBECTL%" set image deployment/springboot-demo springboot-cicd-demo="chethan123444/%IMAGE_NAME%:%BUILD_NUMBER%" --kubeconfig="%KUBECONFIG%"
            if errorlevel 1 exit /b 1

            "%KUBECTL%" rollout status deployment/springboot-demo --timeout=180s --kubeconfig="%KUBECONFIG%"
            if errorlevel 1 exit /b 1

            "%KUBECTL%" get deployments --kubeconfig="%KUBECONFIG%"
            if errorlevel 1 exit /b 1

            "%KUBECTL%" get pods --kubeconfig="%KUBECONFIG%"
            if errorlevel 1 exit /b 1
        '''
    }
}

    post {
        success {
            echo 'CI/CD Pipeline completed successfully!'
        }
        failure {
            echo 'CI/CD Pipeline failed. Check the console output.'
        }
    }
}
