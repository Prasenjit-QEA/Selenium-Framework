pipeline {
    agent any

    tools {
        maven 'maven-3.8.5'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/Prasenjit-QEA/Selenium-Framework.git'
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean install -DskipTests'
            }
        }
        stage('Test') {
            steps {
                // Using continueOnError or similar if you want to ensure reports run
                bat 'mvn test'
            }
        }
        stage('Reports') {
            steps {
                publishHTML(target: [
                    alwaysLinkToLastBuild: true,
                    keepAll              : true,
                    reportDir            : 'target/ExtentReport', // Recommended to output to target/
                    reportFiles          : 'ExtentReport.html',
                    reportName           : 'Extent Spark Report'
                ])
            }
        }
    }

    post {
        always {
            // Corrected glob pattern for artifacts
            archiveArtifacts artifacts: '**/ExtentReport/*.html', allowEmptyArchive: true
            junit 'target/surefire-reports/*.xml'
        }

        success {
            emailext(
                to: 'prasenjitqea@gmail.com',
                subject: "Build Success: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html>
                <body>
                    <p>Hello Team,</p>
                    <p>The latest Jenkins build has completed successfully.</p>
                    <p><b>Project Name:</b> ${env.JOB_NAME}</p>
                    <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                    <p><b>Build Status:</b> <span style="color: green;"><b>SUCCESS</b></span></p>
                    <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p><b>Branch:</b> ${env.GIT_BRANCH}</p>
                    <p><b>Extent Report:</b> <a href="${env.JOB_URL}ws/target/ExtentReport/ExtentReport.html">Click here</a></p>
                    <p>Best regards,<br><b>Automation Team</b></p>
                </body>
                </html>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }

        failure {
            emailext(
                to: 'prasenjitqea@gmail.com',
                subject: "Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                body: """
                <html>
                <body>
                    <p>Hello Team,</p>
                    <p>The latest Jenkins build has <b>FAILED</b>.</p>
                    <p><b>Project Name:</b> ${env.JOB_NAME}</p>
                    <p><b>Build Number:</b> #${env.BUILD_NUMBER}</p>
                    <p><b>Build Status:</b> <span style="color: red;"><b>FAILED &#10060;</b></span></p>
                    <p><b>Build URL:</b> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p><b>Build log is attached to this email.</b></p>
                    <p>Best regards,<br><b>Automation Team</b></p>
                </body>
                </html>
                """,
                mimeType: 'text/html',
                attachLog: true
            )
        }
    }
}