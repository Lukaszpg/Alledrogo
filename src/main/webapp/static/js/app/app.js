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

        angular.element(document).ready(function () {
            getSearchCategories();
        });

        var getSearchCategories = function() {
            $http.get('/alledrogo/get-search-categories').then(function (response) {
                if (response.data.success) {
                    var parsed = JSON.parse(response.data.body);
                    var categoriesSelect = $('#search-categories');

                    categoriesSelect.material_select();

                    for(var i = 0; i < parsed.length; i++) {
                        var option = $('<option value="'+parsed[i]["name"]+'">'+parsed[i]["name"]+'</option>');
                        categoriesSelect.append(option);
                    }

                    categoriesSelect.material_select('update');
                    categoriesSelect.closest('.input-field').children('span.caret').remove();
                } else {
                    $.growl.error({title: "Błąd", message: "Nie udało się pobrać kategorii wyszukiwania."});
                }
            }, function () {
            });
        };

        var self = this;
        $rootScope.$storage = $localStorage;

        var authenticate = function (credentials, callback) {
            var loginData = credentials ? {
                username: credentials.username,
                password: btoa(credentials.password)
            } : {};

            $http.post('/alledrogo/login', loginData).then(function (response) {
                if (response.data.success) {
                    var body = angular.fromJson(response.data.body);
                    $rootScope.$storage.authenticated = true;
                    $rootScope.$storage.role = body.role;
                    $rootScope.$storage.username = body.username;
                    $rootScope.$storage.email = credentials.username;
                    $.growl.notice({title: "Zalogowano", message: "Pomyślnie zalogowano."});
                    $('#loginModal').modal('close');
                } else {
                    $rootScope.$storage.authenticated = false;
                }
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

            $http.post('/alledrogo/save-user', registrationData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Zarejestrowano", message: "Pomyślnie zarejestrowano."});
                    self.registration = {};
                    $rootScope.registerForm.$setUntouched();
                    $rootScope.registerForm.$setPristine();
                }
            });
        };

        var sell = function(auctionData, callback) {
            var requestData = auctionData ? {
                title: auctionData.title,
                description: auctionData.description,
                amount: auctionData.amount,
                category: auctionData.category,
                durationInDays: auctionData.durationInDays,
                price: auctionData.price,
                userEmail: $rootScope.$storage.email
            } : {};

            $http.post('/alledrogo/createAuction', requestData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Wystawiono przedmiot", message: "Pomyślnie utworzono aukcję."});
                }
            });
        };

        var saveBid = function(bid, callback) {
            var bidData = bid ? {
                amount: bid.amount,
                auctionId: $("#auction-id").val(),
                buyerEmail: $rootScope.$storage.email
            } : {};

            $http.post('/alledrogo/auctions/details/bid', bidData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Zalicytowano", message: "Pomyślnie zalicytowano."});
                    $("#bid").val("");
                    setWinningBidOnAmountDisplay(response.data.body);
                } else {
                    $.growl.error({title: "Błąd", message: response.data.message});
                }
            });
        };

        var saveCategory = function(category, callback) {
            var categoryData = category ? {
                    name: category.name
                } : {};

            $http.post('/alledrogo/rest/admin/add-category', categoryData).then(function (response) {
                if (response.data.success) {
                    $.growl.notice({title: "Dodano kategorię", message: "Pomyślnie dodano kategorię."});
                }
            });
        };

        self.credentials = {};
        self.login = function () {
            authenticate(self.credentials, function () {
                if ($rootScope.$storage.authenticated) {
                    $location.path("/");
                    self.error = false;
                } else {
                    $location.path("/alledrogo/login");
                    self.error = true;
                }
            });
        };

        self.logout = function () {
            $http.post('/alledrogo/logout', {}).then(function (response) {
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

        var searchAuctions = function(searchArgs) {
            var searchArguments = searchArgs ? {
                category: searchArgs.category,
                item: searchArgs.item
            } : {};

            $http.post('/alledrogo/search', searchArguments).then(function (response) {
                if (response.data.success) {
                } else {
                }
            });
        }

        self.searchData = {};
        self.sendSearch = function() {
            searchAuctions(self.searchData);
        }

        self.registration = {};
        self.sendRegister = function() {
            register(self.registration);
        };

        self.auction = {};
        self.createAuction = function() {
            sell(self.auction);
        }

        self.bid = {};
        self.placeBid = function() {
            saveBid(self.bid);
        }

        self.category = {};
        self.addCategory = function() {
            saveCategory(self.category);
        }

        var setWinningBidOnAmountDisplay = function(json) {
            var object = JSON.parse(json);
            $('.winning-bid').text(object.amount + " PLN");
        }
    });
