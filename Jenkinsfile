#!groovy

def call(body) {
	def config = [:]
	body.resolveStrategy = Closure.DELEGATE_FIRST
	body.delegate = config
	body()

	def deploymentApprovers = 'dug, cwb, dlh'
	def version
	def gitCommit
	def projectName
	def fullProjectName
	node {
		stage ('SCM and init') {
			dir ('project') {
				checkout scm
				sh "chmod 777 ./gradlew"
				version = sh(returnStdout:true, script: "./gradlew -q printVersion").trim()
				gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
				currentBuild.displayName = version
				projectName = currentBuild.projectName
				fullProjectName = currentBuild.fullProjectName
			}
			dir('deployment') {
				deleteDir() // Clean and clone fresh each time
				git changelog: false, poll: false, url: 'git@bitbucket.org:mahesh/epic-ansible-deploy.git', branch: env.DEPLOY_BRANCH_OVERRIDE ?: 'master'
				sh 'cp ../project/k8s-* .'
			}
		}
		stage('Build') {
			dir ('project') {
				try {
					sh "./gradlew -i ${config.gradleBuildTasks}"
				} finally {
					junit allowEmptyResults: true, testResults: 'build/**/TEST-*.xml'
				}
			}
		}
		stage('Reports') {
			dir ('project') {
				jacoco()
			}
		}
		stage('Build and push docker image') {
			dir ('project') { sh './gradlew -i docker dockerPush' }
		}
	}
	node {
		stage('Deploy To Dev / Candidate for QA') {
			dir ('deployment') {
				archiveArtifacts artifacts: 'k8s-*.yml'
			}
			dir ('project') {
				build job: 'deployment/qa-deployment-candidates', parameters:
					[
					string(name: 'UpstreamProjectName',
							value: projectName),
					string(name: 'UpstreamFullProjectName',
							value: fullProjectName),
					 string(name: 'GitCommit',
						    value: gitCommit),
					 string(name: 'Version',
						    value:version)]
			}
		}
		if (version ==~ /^\d*\.\d*\.\d*$/ ) {
			stage('Candidate for Release') {
				dir ('project') {
					build job: 'deployment/release-deployment-candidates', parameters:
						[
						 string(name: 'UpstreamProjectName',
								value: projectName),
						 string(name: 'UpstreamFullProjectName',
								value: fullProjectName),
						 string(name: 'GitCommit',
								value: gitCommit),
						 string(name: 'Version',
								value:version)]
				}
			}
		}
	}
}


commonPipeline {
	gradleBuildTasks = 'clean build sonarqube'
}
