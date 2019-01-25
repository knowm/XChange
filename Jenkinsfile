#!/usr/bin/env groovy

node {
    stage('checkout') {
        checkout scm
    }

    stage('check java') {
        sh "java -version"
    }

    stage('clean') {
        sh "mvn clean"
    }

    stage('backend tests') {
        try {
            sh "mvn test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }

    stage('deploy') {
        sh "mvn deploy -DskipTests"
        archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
    }
}
