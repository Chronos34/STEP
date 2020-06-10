// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * Adds a random greeting to the page.
 */
function addRandomQuote() {
  const quotes =
      ['"The greatest glory in living lies not in never falling, \
      but in rising every time we fall." -Nelson Mandela', '"The way \
      to get started is to quit talking and begin doing." -Walt Disney',
      '"Many of life\'s failures are people who did not realize how \
      close they were to success when they gave up." -Thomas A. Edison',
      'The supreme art of war is to subdue the enemy without fighting. -\
      Sun Tzu', 'Opportunities multiply as they are seized. -Sun Tzu',
      '"Learning never exhausts the mind." -Leonardo da Vinci',
      '"Learn as if you were not reaching your goal and as though you were \
      scared of missing it" -Confucius'];

  // Pick a random greeting.
  const quote = quotes[Math.floor(Math.random() * quotes.length)];

  // Add it to the page.
  const quoteContainer = document.getElementById('quote-container');
  quoteContainer.innerText = quote;
}

function animeComments() {
  fetch('/data').then(response => response.json()).then((comments) => {
    const commentListElement = document.getElementById('display-anime-comment');
    comments.forEach((comment) => {
      commentListElement.appendChild(createMsgElement(comment));
    })
  });
}

function secularComments() {
  fetch('/data-secular').then(response => response.json()).then((comments) => {
    const commentListElement = document.getElementById('display-secular-comment');
    comments.forEach((comment) => {
      commentListElement.appendChild(createMsgElement(comment));
    })
  });
}

function christianComments() {
  fetch('/data-gospel').then(response => response.json()).then((comments) => {
    const commentListElement = document.getElementById('display-christian-comment');
    comments.forEach((comment) => {
      commentListElement.appendChild(createMsgElement(comment));
    })
  });
}


function createMsgElement(comment) {
  const msgElement = document.createElement('li');
  msgElement.className = 'comment';

  const nameElement = document.createElement('strong');
  nameElement.innerText = comment.identity;

  const statementElement = document.createElement('span');
  statementElement.innerText = `${nameElement.innerText}: ${comment.message}`;

  msgElement.appendChild(statementElement);
  return msgElement;
}

async function deleteGospelComments() {
    const params = new URLSearchParams();
    params.append("kind", "Gospel");
    await fetch('/delete-data', {method: 'POST', body: params});
    location.reload();
}

async function deleteSecularComments() {
    const params = new URLSearchParams();
    params.append("kind", "Secular");
    await fetch('/delete-data', {method: 'POST', body: params});
    location.reload();
}

async function deleteAnimeComments() {
    const params = new URLSearchParams();
    params.append("kind", "Anime");
    await fetch('/delete-data', {method: 'POST', body: params});
    location.reload();
}

async function getUsername() {
    const response = await fetch('/background');
    const name = await response.text();
    if (name.trim().length == 0) {
        document.getElementById('name').innerText = "Anakin Skywalker!";
        return;
    }
    document.getElementById('name').innerText = name.trim() + '!';
}

// This function passes the user's chosen name
// to all commenting servlets to ensure
// each comment is attributed to a user
async function updateServlets() {
    const response = await fetch('/background');
    const names = await response.text();
    if (names.trim().length != 0 ) {
        const params = new URLSearchParams();
        params.append("name", names.trim());
        params.append("preferedNumber", "1");
        await fetch('/data-secular', {method: 'POST', body: params});
        await fetch('/data-gospel', {method: 'POST', body: params});
        await fetch('/data', {method: 'POST', body: params});
    }
}

// logInStatus is called on every page to ensure
// certain prvileges are enjoyed by users who
// verify their identity or chhose an alias.
async function logInStatus() {
    const response = await fetch('/background');
    const name = await response.text();
    const secondResponse = await fetch('/login', {method: 'POST'});
    const isLoggedIn = await secondResponse.text();
    if (name.trim().length == 0 && isLoggedIn.trim() == "false") {
        document.getElementById('signOut').style.display = "none";
        document.getElementById('comment').style.display = "none";
    } else {document.getElementById('signIn').style.display = "none";}
}