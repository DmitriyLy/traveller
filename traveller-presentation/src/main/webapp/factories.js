app.factory('cityService', ['$resource', function($resource) {
    return $resource('/api/cities');
}
]);