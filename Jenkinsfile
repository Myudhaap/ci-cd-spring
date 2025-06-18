pipeline {
    agent any

    environment {
        IMAGE_NAME = "mayutama23/ci-cd-spring"
        DOCKER_CREDENTIALS_ID = 'dockerhub'
        GITHUB_CREDENTIALS_ID = 'github-credentials'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    credentialsId: "${env.GITHUB_CREDENTIALS_ID}",
                    url: 'https://github.com/Myudhaap/ci-cd-spring.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${IMAGE_NAME}:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKER_CREDENTIALS_ID}") {
                        dockerImage.push()
                    }
                }
            }
        }
    }
}