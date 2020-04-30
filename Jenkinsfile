pipeline {
    agent {
        node {
            label 'master'
        }
    }

    options {
        timeout(time: 10, unit: 'MINUTES')
        timestamps()
        skipStagesAfterUnstable()
    }

    stages {
        stage("Prepare") {
            steps {
                sh 'chmod +x gradlew'
            }
        }

//        stage('Test') {
//            steps {
//                // Compile and run the unit tests for the app and its dependencies
//                sh './gradlew testDebugUnitTest testDebugUnitTest'
//
//                // Analyse the test results and update the build result as appropriate
//                junit '**/TEST-*.xml'
//            }
//        }

        stage("Build") {
            steps {
               sh './gradlew clean assembleDebug' // builds app/build/outputs/apk/app-debug.apk
            }
        }

        stage("Archive") {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/**/app-release.apk', excludes: 'app/build/outputs/apk/*-unaligned.apk'
            }
        }

    }
}
