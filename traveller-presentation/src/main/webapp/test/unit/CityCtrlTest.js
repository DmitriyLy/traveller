describe('Testing City Controller', function () {
    beforeEach(module('app'));

    var scope = {};
    var httpBackend = {};

    beforeEach(inject(function ($controller, $httpBackend) {
        $controller('CityCtrl', {$scope: scope});
        httpBackend = $httpBackend;
    }));

    afterEach(function () {

    });

    it('Rows per page should have initial value', function () {
        var ctrl;
        expect(scope.rowsPerPage).toBeDefined();
        expect(scope.rowsPerPage).toBe(10);
    });

    it('Should return false for non-region center', function () {
        var city = {
            name: 'Gribovka',
            district: 'Ovidiopol',
            region: 'Odessa'
        };
        var isCenter = scope.isRegionCenter(city);
        expect(isCenter).toBeFalsy();
    });

    it('Should return true for region center', function () {
        var city = {
            name: 'Kiev',
            district: '',
            region: 'Kiev'
        };
        var isCenter = scope.isRegionCenter(city);
        expect(isCenter).toBeTruthy();
    });

    it('Should query cities', function () {
        httpBackend.expectGET("/api/cities").respond([{
            'name': 'Odessa',
            'district': '',
            'region': 'Odessa'
        }]);

        httpBackend.flush();
        expect(scope.cities[0].name).toBe('Odessa');
        expect(scope.cities[0].district).toBe('');
        expect(scope.cities[0].region).toBe('Odessa');
    });

});