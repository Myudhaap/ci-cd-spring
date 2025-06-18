pipeline {
    agent any

    tools {
        git 'Default'
    }

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
                    url: 'https://github.com/Myudhaap/ci-cd-spring'
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
                        dockerImage.push("latest")
                    }
                }
            }
        }

        stage('Deploy to Minikube K8S') {
            steps {
                script {
                    sh """
                    kubectl config use-context minikube

                    kubectl apply -f k8s/deployment.yaml
                    kubectl apply -f k8s/service.yaml
                    kubectl apply -f k8s/ingress.yaml

                    kubectl set image deployment/‎ci-cd-spring-app-deployment ‎ci-cd-spring-app=${IMAGE_NAME}:${BUILD_NUMBER} --record
                    """
                }
            }
        }
    }
}

