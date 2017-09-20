
module.directive('whoAmI', whoAmIGetter);

function whoAmIGetter() {
	return {
		transclude: true,
		restrict: 'ACE',
		templateUrl: '../footer.htm',
		scope: {
          'ctype': '@',     //memory variable
          'sayname': '&',      //method
		}
	}
}

