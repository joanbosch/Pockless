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
        stage("Checkout") {
            checkout scm
        }

        stage("Prepare") {
            sh 'chmod +x ./gradlew'
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
            if (params.BUILD_CONFIG == 'release') {
                sh './gradlew clean assembleRelease' // builds app/build/outputs/apk/app-release.apk file
            } else {
                sh './gradlew clean assembleDebug' // builds app/build/outputs/apk/app-debug.apk
            }
        }

        stage("Archive") {
            if (params.BUILD_CONFIG == 'release') {
                archiveArtifacts artifacts: 'app/build/outputs/apk/**/app-release.apk', excludes: 'app/build/outputs/apk/*-unaligned.apk'
            } else {
                archiveArtifacts artifacts: 'app/build/outputs/apk/**/app-debug.apk', excludes: 'app/build/outputs/apk/*-unaligned.apk'
            }
        }

    }
}
