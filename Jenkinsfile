node {
  stage ('SCM Checkout') {
    git url: 'https://github.com/DmitriyLy/traveller'
  }
  stage ('Clean Install') {
    sh 'mvn clean install'
  }
}
