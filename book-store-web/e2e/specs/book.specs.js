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

/* global by, element, browser, expect */

var BookPage = require('../pages/book.page');

describe('Book E2E Testing', function () {

    var page = new BookPage();
    var nameVarTest = 'Val' + Math.floor(Math.random() * 10000);
    var descriptionVarTest = 'Val' + Math.floor(Math.random() * 10000);
    var isbnVarTest = 'Val' + Math.floor(Math.random() * 10000);
    var imageVarTest = 'https://avatars1.githubusercontent.com';

    beforeEach(function () {
        browser.get('#/book/list');
    });

    it('should create one book', function () {
        page.createButton.click();
        page.fillForm({
            name: nameVarTest,
            description: descriptionVarTest,
            isbn: isbnVarTest,
            image: imageVarTest
        });
        page.saveButton.click();

        var book = new page.bookDetails();

        expect(book.name.getText()).toBe(nameVarTest);
        expect(book.description.getText()).toBe(descriptionVarTest);
        expect(book.isbn.getText()).toBe(isbnVarTest);
    });

    it('should read one book', function () {
        var book = new page.bookListItem(0);
        book.self.click();
        book = new page.bookDetails();
        expect(book.name.getText()).toBe(nameVarTest);
        expect(book.description.getText()).toBe(descriptionVarTest);
        expect(book.isbn.getText()).toBe(isbnVarTest);
    });

    it('should edit one book', function () {
        var book = new page.bookListItem(0);
        book.editButton.click();

        page.fillForm({
            name: 'New' + nameVarTest,
            description: 'New' + descriptionVarTest,
            isbn: 'New' + isbnVarTest,
            image: imageVarTest + '/1'
        });

        page.saveButton.click();

        book = page.bookDetails();

        expect(book.name.getText()).toBe('New' + nameVarTest);
        expect(book.description.getText()).toBe('New' + descriptionVarTest);
        expect(book.isbn.getText()).toBe('New' + isbnVarTest);
    });

    it('should delete the book', function () {
        var before = page.bookList.count();
        var book = new page.bookListItem(0);

        book.deleteButton.click();
        book.confirmDeleteButton.click();

        var after = page.bookList.count();

        expect(after).toBeLessThan(before);
    });
});
