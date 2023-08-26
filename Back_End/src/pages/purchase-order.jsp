<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Purchase Order Manage</title>
    <meta content="width=device-width initial-scale=1" name="viewport">
    <link href="../assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="../assets/css/styles.css" rel="stylesheet">
    <link crossorigin="anonymous" href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
          integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" rel="stylesheet">
    <style>
        ul > li {
            cursor: pointer;
        }
    </style>

</head>
<body>
<!--header-->
<header class="jumbotron bg-primary text-white p-3">
    <h1 class="position-absolute" id="nav"></h1>
    <ul class="list-group list-group-horizontal text-danger justify-content-end font-weight-bold">
        <li class="list-group-item bg-white" id="lnkHome"><a href="../../index.jsp">Home</a></li>
        <li class="list-group-item bg-white" id="lnkCustomer"><a href="customer.html">Customer</a></li>
        <li class="list-group-item bg-white" id="lnkItem"><a  href="item.jsp">Item</a></li>
        <li class="list-group-item bg-danger text-white" id="lnkOrders"><a class="text-white" href="purchase-order.jsp">Orders</a></li>
    </ul>
</header>

<!--order form-->
<main class="container-fluid" id="orderContent">
    <div class="container-fluid mt-4">
        <!--main tile-->
        <div class="jumbotron pt-4">
            <div class="row">
                <div class="col-lg-4">
                    <div class="card">
                        <div class="card-header bg-primary font-weight-bolder">Invoice Details</div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label>Order ID :</label>
                                            <input class="form-control" id="txtOrderID" type="text">
                                            <span class="control-error"
                                                  id="orderId-invalid-error"></span>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label>Date :</label>
                                            <input class="form-control" id="txtDate" type="date">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label>Customer :</label>
                                            <select class="form-control" id="selectCusID">
                                                <option value="1">A</option>
                                                <option value="2">B</option>
                                            </select>
                                            <span class="control-error"
                                                  id="cus-empty-error"></span>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label for="orderCustomerID">Customer ID</label>
                                            <input class="form-control" id="orderCustomerID">
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label>Name :</label>
                                            <input class="form-control" id="orderCustomerName" type="text">
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label>Salary :</label>
                                            <input class="form-control" id="orderCustomerSalary" type="text">
                                        </div>
                                    </div>
                                    <div class="col-12">
                                        <div class="form-group">
                                            <label>Address :</label>
                                            <input class="form-control" id="orderCustomerAddress" type="text">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-lg-5 mt-lg-0 mt-3">
                    <div class="card">
                        <div class="card-header bg-primary  font-weight-bolder">Item Select</div>
                        <div class="card-body">
                            <form>
                                <div class="row">
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label>Item :</label>
                                            <select class="form-control" id="selectItemCode"></select>
                                            <span class="control-error"
                                                  id="item-empty-error"></span>
                                        </div>
                                    </div>
                                    <div class="col-6">
                                        <div class="form-group">
                                            <label>Item Code :</label>
                                            <input class="form-control" id="txtItemCode" type="text">
                                        </div>
                                    </div>
                                    <div class="col-5">
                                        <div class="form-group">
                                            <label>Item Name :</label>
                                            <input class="form-control" id="txtItemDescription" type="text">
                                        </div>
                                    </div>
                                    <div class="col-4">
                                        <div class="form-group">
                                            <label class="">Price :</label>
                                            <input class="form-control" id="txtItemPrice" type="text">
                                        </div>
                                    </div>
                                    <div class="col-3">
                                        <div class="form-group">
                                            <label class="">QtyOnH :</label>
                                            <input class="form-control" id="txtQTYOnHand" type="text">
                                        </div>
                                    </div>
                                    <hr>
                                    <hr>
                                    <div class="col-12 mt-4">
                                        <div class="form-group">
                                            <label>Order Quantity :</label>
                                            <input class="form-control" id="txtQty" min="1" type="number">
                                            <span class="control-error" id="order-qty-empty-error"></span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group text-right">
                                    <button class="btn btn-danger col-12 col-sm-auto" id="btnAddToTable" type="button">
                                        Add Item
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="col-lg-3">
                    <section class="content-box-2 p-4 ">
                        <div class="row">
                            <div class="col-12">
                                <div class="row">
                                    <div class="col-12">
                                        <span class="" id="total-text">Total : </span>
                                    </div>
                                    <div class="col-12">
                                        <span id="total">00.0</span> Rs/=
                                    </div>
                                </div>

                            </div>
                            <div class="col-12">
                                <div class="row">
                                    <div class="col-12">
                                        <span id="total-text1">SubTotal : </span>
                                    </div>
                                    <div class="col-12">
                                        <span id="subtotal">00.0</span> Rs/=
                                    </div>
                                </div>

                            </div>
                            <div class="col-6 mt-2">
                                <div class="form-group">
                                    <label>Cash</label>
                                    <input class="form-control" id="txtCash" min="0" type="number">
                                    <span class="control-error"
                                          id="cash-empty-error"></span>
                                </div>
                            </div>
                            <div class="col-6 mt-2">
                                <div class="form-group">
                                    <label>Discount</label>
                                    <input class="form-control" id="txtDiscount" min="0" type="number">
                                </div>
                            </div>
                            <div class="col-8">
                                <div class="form-group">
                                    <label>Balance</label>
                                    <input class="form-control" id="txtBalance" type="text">
                                </div>
                            </div>
                            <div class="col-4 mt-4">
                                <button class="btn btn-success" id="btnSubmitOrder" type="button">Purchase</button>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
            <!--order table-->
            <div class="col-md-12 mt-4" style="height:180px; overflow-y: scroll">
                <table class="table table-bordered text-white">
                    <thead class="bg-primary">
                    <tr>
                        <th>Item Code</th>
                        <th>Item Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody class="bg-light text-dark" id="orderTable">

                    </tbody>
                </table>
            </div>
            <!---------->
        </div>
    </div>
</main>


<script src="../assets/js/jquery-3.7.0.min.js"></script>

</body>
</html>
