node {
	properties([
	  parameters([
		string(name: 'SELENIUM_URL', defaultValue: 'https://jupiter2.cloud.planittesting.com'),
		string(name: 'SELENIUM_BROWSER', defaultValue: 'firefox'),
		string(name: 'SELENIUM_WAIT', defaultValue: '3'),
		string(name: 'SELENIUM_GRID_URL', defaultValue: 'http://selenium_hub:4444/wd/hub'),
		string(name: 'SELENIUM_HEADLESS', defaultValue: 'true'),
		string(name: 'PARALLEL_TESTS', defaultValue: 'true'),
        choice(name: 'LOGGING_LEVEL', choices: ['INFO', 'FINE'], defaultValue:'INFO', description: 'Log level for WebDriver actions'),
		string(name: 'DOT_ENDPOINT', defaultValue: 'https://ofzwv8iiqc.execute-api.ap-southeast-2.amazonaws.com/prod/junit'),
		string(name: 'DOT_API_KEY', defaultValue:'IvexKZnKGa41EAuhpyqj32UwjiCpIO7q4d52pQ14')
	  ])
	])
	checkout scm
    def testImage = docker.build("jupitertoys-java-test-image", "./.devcontainer") 
	try {
		testImage.inside('--network=ci_planittesting -v $WORKSPACE/.m2:/root/.m2') {
			stage('Prepare') {
				sh "mvn clean"
				sh "mvn dependency:resolve"
				sh "mvn test-compile"
			}
			stage ('Run Tests') {
				withEnv(["SELENIUM_HEADLESS=${params.SELENIUM_HEADLESS}", "SELENIUM_GRID_URL=${params.SELENIUM_GRID_URL}", "SELENIUM_URL=${params.SELENIUM_URL}", "SELENIUM_BROWSER=${params.SELENIUM_BROWSER}", "SELENIUM_WAIT=${params.SELENIUM_WAIT}"]) {
					try {
						sh "mvn -Dparallel-test=${params.PARALLEL_TESTS} -Dlogging_level=${params.LOGGING_LEVEL} test"
						sh "mvn -Dtest=CucumberRunner -Dparallel-test=${params.PARALLEL_TESTS} -Dlogging_level=${params.LOGGING_LEVEL} test"
					} finally {
						junit 'target/surefire-reports/*.xml'
						stash includes: 'target/surefire-reports/*.xml', name: 'junit-results'
						stash includes: 'test-metadata.yaml', name: 'test-metadata'
					}
				}
			}
		}	
	} finally {
		stage ('Upload Results') {
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
