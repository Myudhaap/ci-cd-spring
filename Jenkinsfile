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
                    url: 'https://github.com/username/repo.git'
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

        stage('Deploy to Minikube') {
            steps {
                script {
                    sh """
                    kubectl config use-context minikube
                    kubectl set image deployment/myapp-deployment myapp=${IMAGE_NAME}:${env.BUILD_NUMBER} --record
                    """
                }
            }
        }
    }
}