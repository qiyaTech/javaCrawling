/**
 * Created by qiyalm on 16/6/14.
 */
(function(){
    var treeGridController = angular.module('treeGridController', ['treeGrid']);

    treeGridController.controller('treeGridCtrl', ['$scope', '$http',function($scope, $http) {

        $scope.resChange=function(){
            $http.get('/resourceManage/getTreeGrid').success(function(data) {
                var treeData = data.map;
                var myTreeData = getTree(treeData, 'resId', 'parentId');
                $scope.tree_data = myTreeData;
            });

        }

        $http.get('/resourceManage/getTreeGrid').success(function(data) {
            var tree;
            var treeData = data.map;
            var myTreeData = getTree(treeData, 'resId', 'parentId');
            $scope.tree_data = myTreeData;
            $scope.my_tree = tree={};
        });

        $scope.my_tree_handler = function (branch) {
            $scope.child=branch.children.length;
            $scope.code=branch.code;
            $scope.name=branch.name;
            $scope.url=branch.url;
            $scope.fatherId=branch.parentId;
            $scope.resId=branch.resId;
            $scope.fatherName=branch.parentName;
        }

        function getTree(data, primaryIdName, parentIdName) {
            if (!data || data.length == 0 || !primaryIdName || !parentIdName)
                return [];

            var tree = [],
                rootIds = [],
                item = data[0],
                primaryKey = item[primaryIdName],
                treeObjs = {},
                parentId,
                parent,
                len = data.length,
                i = 0;

            while (i < len) {
                item = data[i++];
                primaryKey = item[primaryIdName];
                treeObjs[primaryKey] = item;
                parentId = item[parentIdName];

                if (parentId) {
                    parent = treeObjs[parentId];

                    if (parent.children) {
                        parent.children.push(item);
                    } else {
                        parent.children = [item];
                    }
                } else {
                    rootIds.push(primaryKey);
                }
            }

            for (var i = 0; i < rootIds.length; i++) {
                tree.push(treeObjs[rootIds[i]]);
            }
            ;

            return tree;
        }

        $scope.tree_data = [];
        $scope.my_tree ={};
        $scope.expandingProperty = {
            field: "name",
            displayName: "菜单名称",
            sortable: true,
            filterable: true,
            cellTemplate: "<i>{{row.branch[expandingProperty.field]}}</i>"
        };
        $scope.col_defs = [
            {
                field: "code",
                displayName:"菜单编号",
                sortable: true,
                sortingType: "string"
            },
            {
                field: "url",
                displayName:"菜单地址",
                sortable: true,
                sortingType: "string",
                filterable: true
            }
        ];
    }])
}).call(this);


