#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "chmod +x mvnw"
         sh "./mvnw clean"
    }

    stage('deploy') {
        sh "./mvnw deploy -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }
}
