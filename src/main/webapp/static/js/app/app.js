'use strict';

angular.module('alledrogoApp', ['ngRoute', 'angular-loading-bar', 'ngAnimate', 'ngStorage'])
    .config(function ($routeProvider, $httpProvider) {

        $routeProvider.when('/login', {
            controller: 'template',
            controllerAs: 'template'
        }).when('/register', {
            templateUrl: 'templates/fragments/register',
            controller: 'template',
            controllerAs: 'template'
        }).when('/admin', {
            templateUrl: 'templates/fragments/admin',
            controller: 'template',
            controllerAs: 'template'
        }).when('/logout', {
            templateUrl: 'templates/index',
            controller: 'template',
            controllerAs: 'template'
        }).when('/index', {
            templateUrl: 'templates/index'
        }).otherwise('/');

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    })
    .controller('template', function ($rootScope, $http, $location, $localStorage, $sessionStorage, $window) {

        var self = this;
        $rootScope.$storage = $localStorage;

        var authenticate = function (credentials, callback) {
            var loginData = credentials ? {
                login: credentials.email,
                password: btoa(credentials.password)
            } : {};

            $http.post('login', loginData).then(function (response) {
                if (response.data.success) {
                    var body = angular.fromJson(response.data.body);
                    $rootScope.$storage.authenticated = true;
                    $rootScope.$storage.role = body.role;
                    $rootScope.$storage.username = body.username;
                    $rootScope.$storage.email = credentials.email;
                    $.growl.notice({title: "Zalogowano", message: "Pomyślnie zalogowano."});
                    $('#loginModal').modal('close');
                } else {
                    $rootScope.$storage.authenticated = false;
                }
                callback && callback();
            }, function () {
                $rootScope.$storage.authenticated = false;
                callback && callback();
            });

        };

        var register = function(registration, callback) {
            var registrationData = registration ? {
                login: registration.email,
                password: btoa(registration.password),
                zipcode: registration.zipcode,
                city: registration.city,
                address: registration.address,
                nickname: registration.nickname,
                name: registration.name,
                surname: registration.surname,
                email: registration.email
            } : {};

            $http.post('save-user', registrationData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Zarejestrowano", message: "Pomyślnie zarejestrowano."});
                    self.registration = {};
                    $rootScope.registerForm.$setUntouched();
                    $rootScope.registerForm.$setPristine();
                }
                callback && callback();
            }, function () {
                callback && callback();
            });
        };

        var sell = function(auctionData, callback) {
            var requestData = auctionData ? {
                title: auctionData.title,
                description: auctionData.description,
                amount: auctionData.amount,
                category: auctionData.category,
                endDate: auctionData.endDate,
                price: auctionData.price,
                userEmail: $rootScope.$storage.email
            } : {};

            $http.post('createAuction', requestData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Wystawiono przedmiot", message: "Pomyślnie utworzono aukcję."});
                }
                callback && callback();
            }, function () {
                callback && callback();
            });
        };

        var saveBid = function(bid, callback) {
            var bidData = bid ? {
                amount: bid.amount,
                auctionId: $("#auction-id").val(),
                buyerEmail: $rootScope.$storage.email
            } : {};

            $http.post('bid', bidData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Zalicytowano", message: "Pomyślnie zalicytowano."});
                }
                callback && callback();
            }, function () {
                callback && callback();
            });
        };

        var saveCategory = function(category, callback) {
            var categoryData = category ? {
                    name: category.name
                } : {};

            $http.post('save-category', categoryData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Dodano kategorię", message: "Pomyślnie dodano kategorię."});
                }
                callback && callback();
            }, function () {
                callback && callback();
            });
        };

        self.credentials = {};
        self.login = function () {
            authenticate(self.credentials, function () {
                if ($rootScope.$storage.authenticated) {
                    $location.path("/");
                    self.error = false;
                } else {
                    $location.path("/login");
                    self.error = true;
                }
            });
        };

        self.logout = function () {
            $http.post('logout', {}).then(function (response) {
                if (response.data.success) {
                    $rootScope.$storage.authenticated = false;
                    $.growl.notice({title: "Wylogowano", message: "Pomyślnie wylogowano."});
                    delete $rootScope.$storage.authenticated;
                    delete $rootScope.$storage.username;
                    delete $rootScope.$storage.email;
                    delete $rootScope.$storage.role;
                    $window.location.href = '/alledrogo';
                } else {
                    $.growl.error({title: "Błąd", message: "Przy wylogowywaniu wystąpił błąd."});
                }
            });
        };

        self.registration = {};
        self.sendRegister = function() {
            register(self.registration, function() {
                if(response.data.success) {
                }
            })
        };

        self.auction = {};
        self.createAuction = function() {
            sell(self.auction, function() {
                if(response.data.success) {
                }
            })
        }

        self.bid = {};
        self.placeBid = function() {
            saveBid(self.bid, function() {
                if(response.data.success) {
                }
            })
        }

        self.category = {};
        self.addCategory = function() {
            saveCategory(self.category, function() {
                if(response.data.success) {

                }
            })
        }
    });
