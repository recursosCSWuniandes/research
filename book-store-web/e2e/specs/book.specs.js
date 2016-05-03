/*
The MIT License (MIT)

Copyright (c) 2015 Los Andes University

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
describe('Book E2E Testing', function () {

	browser.driver.manage().window().maximize();

	var nameVarTest = 'Val' + Math.floor(Math.random() * 10000);
	var descriptionVarTest = 'Val' + Math.floor(Math.random() * 10000);
	var isbnVarTest = 'Val' + Math.floor(Math.random() * 10000);
	var imageVarTest = 'Val' + Math.floor(Math.random() * 10000);

    beforeEach(function () {
        browser.addMockModule('ngCrudMock', function () {
            var mod = angular.module('ngCrudMock');

            mod.run(['ngCrudMock.mockRecords', function(records){
                records['books'] = [];

                records['editorials'] = [];
                records['editorials'].push({id: Math.floor(Math.random() * 10000), name: 'editorial'});
            }]);
        });
    });

    it('should create one book', function () {
        browser.get('#/book');
        element(by.id('create-book')).click();
        element(by.id('name')).sendKeys(nameVarTest);
        element(by.id('description')).sendKeys(descriptionVarTest);
        element(by.id('isbn')).sendKeys(isbnVarTest);
        element(by.id('image')).sendKeys(imageVarTest);
        element(by.id('editorial')).all(by.css('option')).last().click();
        element(by.id('save-book')).click();
        expect(element.all(by.repeater('record in records')).count()).toEqual(1);
    });

    it('should read one book', function () {
        expect(element(by.id('0-name')).getText()).toBe(nameVarTest);
        expect(element(by.id('0-description')).getText()).toBe(descriptionVarTest);
        expect(element(by.id('0-isbn')).getText()).toBe(isbnVarTest);
        expect(element(by.id('0-image')).getText()).toBe(imageVarTest);
    });

    it('should edit one book', function () {
        element(by.id('0-edit-btn')).click();

        element(by.id('name')).clear().sendKeys('New' + nameVarTest);
        element(by.id('description')).clear().sendKeys('New' + descriptionVarTest);
        element(by.id('isbn')).clear().sendKeys('New' + isbnVarTest);
        element(by.id('image')).clear().sendKeys('New' + imageVarTest);

        element(by.id('save-book')).click();

        expect(element(by.id('0-name')).getText()).toBe('New' + nameVarTest);
        expect(element(by.id('0-description')).getText()).toBe('New' + descriptionVarTest);
        expect(element(by.id('0-isbn')).getText()).toBe('New' + isbnVarTest);
        expect(element(by.id('0-image')).getText()).toBe('New' + imageVarTest);
    });

    it('should delete the book', function () {
        element(by.id('0-delete-btn')).click();
        expect(element.all(by.id('0-name')).count()).toEqual(0);
        expect(element.all(by.id('0-description')).count()).toEqual(0);
        expect(element.all(by.id('0-isbn')).count()).toEqual(0);
        expect(element.all(by.id('0-image')).count()).toEqual(0);
        expect(element.all(by.id('0-publishDate')).count()).toEqual(0);
    });
});
