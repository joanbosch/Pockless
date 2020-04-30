pipeline {
    agent {
        node {
            label 'victor'
        }
    }

    options {
        timeout(time: 10, unit: 'MINUTES')
        timestamps()
        skipStagesAfterUnstable()
         buildDiscarder(
                logRotator(
                    // number of build logs to keep
                    numToKeepStr:'5',
                    // history to keep in days
                    daysToKeepStr: '15',
                    // artifacts are kept for days
                    artifactDaysToKeepStr: '15',
                    // number of builds have their artifacts kept
                    artifactNumToKeepStr: '5'
                )
            )
    }

    stages {

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
               bat 'gradlew clean assembleDebug' // builds app/build/outputs/apk/app-debug.apk
            }
        }

        stage("Archive") {
            steps {
                archiveArtifacts artifacts: 'app/build/outputs/apk/**/app-release.apk', excludes: 'app/build/outputs/apk/*-unaligned.apk'
            }
        }
    }

    post {
         always {
             deleteDir() /* clean up our workspace */
          }
    }
}
