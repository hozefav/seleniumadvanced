node {
    properties([
        parameters([
            string(name: 'SELENIUM_URL', defaultValue: 'http://automationpractice.com'),
            string(name: 'SELENIUM_BROWSER', defaultValue: 'firefox'),
            string(name: 'SELENIUM_WAIT', defaultValue: '1'),
            string(name: 'SELENIUM_GRID_URL', defaultValue: 'http://selenium_hub:4444/wd/hub'),
            choice(name: 'SELENIUM_HEADLESS', choices: ['true'], defaultValue: 'true'),
            choice(name: 'logging_level', choices: ['INFO', 'FINE'], defaultValue:'INFO', description: 'log level for webdriver actions'),
            string(name: 'DOT_ENDPOINT', defaultValue: 'https://ofzwv8iiqc.execute-api.ap-southeast-2.amazonaws.com/prod/junit'),
		    string(name: 'DOT_API_KEY', defaultValue:'IvexKZnKGa41EAuhpyqj32UwjiCpIO7q4d52pQ14') 
        ])
    ])

    checkout scm
    try {
        def testImage = docker.build("${JOB_NAME}-image", "./.devcontainer")
        testImage.inside('--network=ci_planittesting -v $WORKSPACE/.m2:/root/.m2') {

            stage('prepare') {
                sh "mvn clean"
                sh "mvn test-compile"
            }

            stage('run tests') {
                withEnv(["SELENIUM_HEADLESS=${params.SELENIUM_HEADLESS}", "SELENIUM_GRID_URL=${params.SELENIUM_GRID_URL}", "SELENIUM_URL=${params.SELENIUM_URL}", "SELENIUM_BROWSER=${params.SELENIUM_BROWSER}", "SELENIUM_WAIT=${params.SELENIUM_WAIT}"]) {
                    try {
                        sh "mvn -Dlogging.level=${params.logging_level} test"
                    } finally {
                        junit 'target/surefire-reports/*.xml'
                        stash includes: 'target/surefire-reports/*.xml', name: 'junit-results'
                        stash includes: 'test-metadata.yaml', name: 'test-metadata'
                    }
                }
            }
        }
    } finally {
        stage('upload results') {
            def run_id = UUID.randomUUID().toString()
            unstash 'junit-results'
            unstash 'test-metadata'
            def files = findFiles(glob: '**/TEST-*.xml')
            for(file in files) {
                updateYaml(file.path)
                def xml_string = readFile(file.path)
                def yaml_string = readFile('test-metadata.yaml')
                sh "curl --location --request POST '${params.DOT_ENDPOINT}/${run_id}' -H 'Content-Type: application/x-www-form-urlencoded' -H 'x-api-key: ${params.DOT_API_KEY}' --form-string 'data=${xml_string}' --form-string 'metadata=${yaml_string}'"
            }
        }
    }
}

def updateYaml(String file) {
	def browser_version = ""
	def capabilities = sh(script: 'curl -s http://selenium_hub:4444/grid/console | grep -ioE  "title=\'{(.*)}\'" | sort --unique | grep -Po "(?<=browserName=)(.*?)(?=,)|(?<=version=)(.*?)(?=,)"', returnStdout: true).split('\n')
	def version = "";
	for (int i=0; i<capabilities.length; i+=2) {
		if(capabilities[i].equalsIgnoreCase(params.SELENIUM_BROWSER)) {
			version = capabilities[i+1];
		}
	}
	def test_metadata = readYaml file: 'test-metadata.yaml'
	if(file.toLowerCase().contains('cucumber') && test_metadata.test.framework.indexOf('cucumber')<0) {
		test_metadata.test.framework.add('cucumber');
	}
	if(!file.toLowerCase().contains('cucumber') && test_metadata.test.framework.indexOf('cucumber')>=0) {
		test_metadata.test.framework.remove('cucumber');
	}
	test_metadata.test.put('browser',params.SELENIUM_BROWSER)
	test_metadata.test.put('browserVersion',version)
	writeYaml file: 'test-metadata.yaml', data: test_metadata, overwrite: true
}