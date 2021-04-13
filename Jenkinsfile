node {
    properties([
        parameters([
            string(name: 'SELENIUM_URL', defaultValue: 'http://automationpractice.com'),
            string(name: 'SELENIUM_BROWSER', defaultValue: 'firefox'),
            string(name: 'SELENIUM_WAIT', defaultValue: '1'),
            string(name: 'SELENIUM_GRID_URL', defaultValue: 'http://selenium_hub:4444/wd/hub'),
            choice(name: 'SELENIUM_HEADLESS', choices: ['true'], defaultValue: 'true')
        ])
    ])

    checkout scm
    def testImage = docker.build("automationjupiter-toys-image","./.devcontainer")
    testImage.inside('--network=ci_planittesting -v $HOME/.m2:/root/.m2'){

        stage('prepare'){
            sh "mvn clean"
            sh "mvn dependency:resolve"
            sh "mvn test compile"
        }

        stage('run tests'){
            withEnv(["SELENIUM_HEADLESS=${params.SELENIUM_HEADLESS}", "SELENIUM_GRID_URL=${params.SELENIUM_GRID_URL}", "SELENIUM_URL=${params.SELENIUM_URL}", "SELENIUM_BROWSER=${params.SELENIUM_BROWSER}", "SELENIUM_WAIT=${params.SELENIUM_WAIT}"]){
                try{
                sh "mvn test"
                } finally {
                    junit 'target/surefire-reports/*.xml'
                }
        }

    }
    


}