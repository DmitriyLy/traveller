node {
  stage ('SCM Checkout') {
    git url: 'https://github.com/DmitriyLy/traveller'
  }
  stage ('Compile-Package') {
    sh 'mvn package'
  }
}
