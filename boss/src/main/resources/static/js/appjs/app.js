/**
 * Created by qiyalm on 16/5/31.
 */

var fishTankApp = angular.module('fishTankApp', [
    'ngRoute',
    'treeGridController',
]);
fishTankApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.useXDomain = true;
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
    delete $httpProvider.defaults.headers.common["X-Requested-With"]
    $httpProvider.defaults.headers.common["Accept"] = "application/json";
}]);