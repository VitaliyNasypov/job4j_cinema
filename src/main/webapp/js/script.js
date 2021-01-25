setTimeout(() => updateHall(), 100);
setInterval(() => updateHall(), 10000);

function addHall(json) {
    let arrayElem = json;
    let countRow = 1;
    let countPlace = 0;
    console.log(arrayElem[0]);
    document.getElementById('nameHall').innerText = '  Hall: ' + arrayElem[0].title;
    if (document.getElementById('1.1') === null) {
        for (let i = 0; i < arrayElem[0].capacity; i++) {
            countPlace++;
            let div = document.createElement('div');
            div.className = "seatsFree";
            div.id = countRow + '.' + countPlace;
            document.querySelector('#hall').append(div);
            if (countPlace === Number(arrayElem[0].numberOfPlaces)) {
                countRow++;
                countPlace = 0;
            }
        }
    }
    arrayElem.forEach(function (item, index) {
        if (index > 0) {
            document.getElementById(String(item.row + "." + item.place))
                .setAttribute('class', 'seatsSold');
        }
    });
}

async function updateHall() {
    const response = await fetch('http://localhost:8080/job4j_cinema/halls', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify({sessionId: 1})
    });
    if (response.ok) {
        const json = await response.json();
        addHall(json)
    } else {
        console.log("Error HTTP: " + response.status);
    }
}

function selectSeat(event) {
    let selectDiv = event.target.getAttribute('id');
    if (document.querySelector('.seatSelect') != null) {
        document.querySelector('.seatSelect').setAttribute('class', 'seatsFree');
    }
    if (document.getElementById(selectDiv).getAttribute('class') === 'seatsFree') {
        seat = 'Selected: row ' + selectDiv.charAt(0) + ' place ' + selectDiv.charAt(2);
        amount = 'Price: 200';
        document.getElementById(selectDiv).setAttribute('class', 'seatSelect');
        document.getElementById('row').setAttribute('value', selectDiv.charAt(0));
        document.getElementById('place').setAttribute('value', selectDiv.charAt(2));
        document.getElementById('price').setAttribute('value', '200');
    }
    if (document.getElementById(selectDiv).getAttribute('class') === 'seatsSold') {
        seat = 'Sorry, place sold';
        amount = '';
    }
    document.getElementById("selectSeatNumber").innerText = seat;
    document.getElementById("selectSeatAmount").innerText = amount;
}

async function submitPay() {
    let username = document.getElementById("username").value;
    let phone = document.getElementById("phone").value;
    let row = document.getElementById("row").value;
    let place = document.getElementById("place").value;
    let price = document.getElementById("price").value;
    if (validate()) {
        const response = await fetch('http://localhost:8080/job4j_cinema/payment', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: JSON.stringify({
                sessionId: 1, row: row, place: place,
                price: price, userName: username, phoneUser: phone
            })
        });
        if (response.ok) {
            const json = await response.json();
            if (json) {
                document.getElementById("selectSeatNumber").innerText = 'Ticket successfully purchased';
                document.getElementById("selectSeatAmount").innerText = '';
                document.getElementById('row').setAttribute('value', '0');
                document.getElementById('place').setAttribute('value', '0');
                document.getElementById('price').setAttribute('value', '0');
                document.getElementById('username').setAttribute('value', '');
                document.getElementById('phone').setAttribute('value', '');
                await updateHall();
            }
        } else {
            console.log("Error HTTP: " + response.status);
        }
    }
}

function validate() {
    let checkValidate = true;
    const regPhone = /^[+]?[\s./0-9]*[(]?[0-9]{1,4}[)]?[-\s./0-9]*$/g;
    if (document.getElementById("username").value.length === 0) {
        document.getElementById("username_empty").innerText = 'Please enter Name';
        checkValidate = false;
    } else {
        document.getElementById("username_empty").innerText = '';
    }
    if (document.getElementById("phone").value.length === 0
        || !regPhone.test(document.getElementById("phone").value)) {
        document.getElementById("phone_empty").innerText = 'Please enter Phone';
        checkValidate = false;
    } else {
        document.getElementById("phone_empty").innerText = '';
    }
    if (document.getElementById("row").value.length === 0) {
        checkValidate = false;
    }
    return checkValidate;
}
