describe('Testing City Controller', function () {
    beforeEach(module('app'));

    var scope = {};

    beforeEach(inject(function ($controller) {
        $controller('CityCtrl', {$scope: scope})
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

});