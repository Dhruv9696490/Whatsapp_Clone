package com.example.whatsappclone.welcomeScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.whatsappclone.NavScreen.Calls
import com.example.whatsappclone.NavScreen.Communication
import com.example.whatsappclone.NavScreen.Update
import com.example.whatsappclone.R
import com.example.whatsappclone.data.DropDownData
import com.example.whatsappclone.data.NavData
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navScreen.Chats
import com.example.whatsappclone.navigation.Routes
import com.example.whatsappclone.navigation.Todo
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(nav:(UserProfile)->Unit,navHostController: NavHostController){
    val context = LocalContext.current
    val navIndex = remember { mutableIntStateOf(0) }
    val viewmodel: ViewModelWhatsApp = viewModel()
    val items = listOf(
        NavData(image = painterResource(id = R.drawable.chat_icon),"Chats"),
        NavData(image = painterResource(id = R.drawable.update_icon),"Status"),
        NavData(image = painterResource(id = R.drawable.communities_icon),"Communities"),
        NavData(image = painterResource(id = R.drawable.telephone),"Calls"),
    )
    var userList by remember { mutableStateOf< List<UserProfile>>(emptyList()) }
    var searchList by remember { mutableStateOf< List<UserProfile>>(emptyList()) }
    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users").get().addOnCompleteListener {
            if (it.isSuccessful){
                val list = it.result.toObjects(UserProfile::class.java).plus(it.result.toObjects(UserProfile::class.java)).plus(it.result.toObjects(UserProfile::class.java)).plus(it.result.toObjects(UserProfile::class.java))
                searchList = list
            }
        }
    }
    userList=searchList
    Scaffold(
        floatingActionButton = {
            if(navIndex.intValue==0){
                FloatingActionButton(
                    onClick = { Todo.showToast(context,"Pending") },
                    containerColor = colorResource(id = R.color.light_green)
                ){
                    Icon(painter = painterResource(id = R.drawable.add_chat_icon),null,
                        tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }else if(navIndex.intValue==1){
                FloatingActionButton(
                    onClick = { Todo.showToast(context,"Pending") },
                    containerColor = colorResource(id = R.color.light_green)
                ){
                    Icon(painter = painterResource(id = R.drawable.camera),null,
                        tint = Color.White, modifier = Modifier.size(32.dp))
                }
            }else if(navIndex.intValue==3){
                FloatingActionButton(
                    onClick = { Todo.showToast(context,"Pending")},
                    containerColor = colorResource(id = R.color.light_green)
                ){
                    Icon(painter = painterResource(id = R.drawable.telephone),null,
                        tint = Color.White, modifier = Modifier.size(43.dp))
                }
            }
        },
        bottomBar = {
            BottomAppBar {
                NavigationBar{
                items.forEachIndexed{index,data->
                        NavigationBarItem(
                            onClick = {navIndex.intValue = index},
                            icon = { Image(painter = data.image,null) },
                            selected = index==navIndex.intValue,
                            label = { Text(data.name) },
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }
        },
        topBar = {
            Column{
                when (navIndex.intValue){
                    0 ->{
                        val expandableDrop = remember { mutableStateOf(false) }
                        val expandableSearch = remember { mutableStateOf(false) }
                        var value by remember { mutableStateOf("") }
                        Column{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                if(!expandableSearch.value){
                                    Text(
                                        " WhatsApp",
                                        fontSize = 32.sp, color = colorResource(id = R.color.light_green),
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row{
                                        IconButton(onClick = { Todo.showToast(context, "Pending") }) {
                                            Icon(
                                                painter = painterResource(R.drawable.camera), null,
                                                modifier = Modifier.size(28.dp)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(4.dp))
                                        IconButton(onClick = { expandableSearch.value=true }) {
                                            Icon(
                                                Icons.Default.Search, null,
                                                modifier = Modifier.size(30.dp)
                                            )
                                        }
                                        Box(contentAlignment = Alignment.Center) {
                                            IconButton(onClick = {
                                                expandableDrop.value = true
                                            }) {
                                                Icon(
                                                    Icons.Default.MoreVert, null,
                                                    modifier = Modifier.size(30.dp)
                                                )
                                            }
                                            DropdownMenu(
                                                expanded = expandableDrop.value,
                                                onDismissRequest = { expandableDrop.value = false }) {
                                                listOf<DropDownData>(
                                                    DropDownData(Icons.Default.Settings, "Settings"),
                                                    DropDownData(Icons.Default.Info, "Information"),
                                                    DropDownData(Icons.Default.Add, "Add Info"),
                                                    DropDownData(Icons.Default.Notifications, "Notification"),
                                                    DropDownData(Icons.AutoMirrored.Filled.ArrowBack, "Logout")
                                                ).onEachIndexed {index,it->
                                                    DropdownMenuItem(
                                                        text = { Text(it.name) },
                                                        onClick = {
                                                            if(index==0){
                                                                navHostController.navigate(Routes.Setting.toString())
                                                            }else if(index==4){
                                                                viewmodel.signOut()
                                                                navHostController.navigate(Routes.Login.toString()){
                                                                    popUpTo(Routes.Home.toString()){inclusive=true}
                                                                }
                                                            }
                                                        },
                                                        leadingIcon = { Icon(it.image, null) })
                                                }
                                            }
                                        }
                                    }
                                }else{
                                    OutlinedTextField(
                                        value = value, onValueChange = { itt ->
                                            value = itt
                                            userList=if(value.isEmpty()){
                                                searchList
                                            }else{
                                                searchList.filter{item->
                                                    item.name.contains(value, ignoreCase = true)
                                                }.distinctBy{it.name}
                                            }},
                                        modifier = Modifier
                                            .fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                                        leadingIcon = {
                                            IconButton(onClick = {
                                                expandableSearch.value=false
                                            }){
                                                Icon(
                                                    Icons.AutoMirrored.Filled.ArrowBack, null,
                                                    modifier = Modifier.size(30.dp)
                                                )
                                            }
                                        },
                                        shape = CircleShape
                                    )
                                }
                            }
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    1 -> {
                        val expandable = remember { mutableStateOf(false) }
                        Column{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    " Update",
                                    fontSize = 32.sp, color = colorResource(id = R.color.black),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {Todo.showToast(context,"Pending")}) {
                                    Icon(
                                        painter = painterResource(R.drawable.camera), null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                IconButton(onClick = {Todo.showToast(context,"Pending")}) {
                                    Icon(
                                        Icons.Default.Search, null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                Box(contentAlignment = Alignment.Center) {
                                    IconButton(onClick = {
                                        expandable.value = true
                                    }) {
                                        Icon(
                                            Icons.Default.MoreVert, null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expandable.value,
                                        onDismissRequest = { expandable.value = false }) {
                                        listOf<DropDownData>(
                                            DropDownData(Icons.Default.Settings, "Settings"),
                                            DropDownData(Icons.Default.Info, "Information"),
                                            DropDownData(Icons.Default.Add, "Add Info"),
                                            DropDownData(Icons.Default.Notifications, "Notification"),
                                            DropDownData(Icons.AutoMirrored.Filled.ArrowBack, "Logout")
                                        ).onEachIndexed {index,it->
                                            DropdownMenuItem(
                                                text = { Text(it.name) },
                                                onClick = {
                                                    if(index==0){
                                                        navHostController.navigate(Routes.Setting.toString())
                                                    }else if(index==4){
                                                        viewmodel.signOut()
                                                        navHostController.navigate(Routes.Login.toString()){
                                                            popUpTo(Routes.Home.toString()){inclusive=true}
                                                        }
                                                    }
                                                },
                                                leadingIcon = { Icon(it.image, null) })
                                        }
                                    }
                                }
                            }
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    2 -> {
                        val expandable = remember { mutableStateOf(false) }
                        Column{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    " Communities",
                                    fontSize = 32.sp, color = colorResource(id = R.color.black),
                                    fontWeight = FontWeight.SemiBold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {Todo.showToast(context,"Pending")}) {
                                    Icon(Icons.Default.Search, null,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Box(contentAlignment = Alignment.Center) {
                                    IconButton(onClick = {
                                        expandable.value = true
                                    }) {
                                        Icon(
                                            Icons.Default.MoreVert, null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expandable.value,
                                        onDismissRequest = { expandable.value = false }) {
                                        listOf<DropDownData>(
                                            DropDownData(Icons.Default.Settings, "Settings"),
                                            DropDownData(Icons.Default.Info, "Information"),
                                            DropDownData(Icons.Default.Add, "Add Info"),
                                            DropDownData(Icons.Default.Notifications, "Notification"),
                                            DropDownData(Icons.AutoMirrored.Filled.ArrowBack, "Logout")
                                        ).onEachIndexed {index,it->
                                            DropdownMenuItem(
                                                text = { Text(it.name) },
                                                onClick = {
                                                    if(index==0){
                                                        navHostController.navigate(Routes.Setting.toString())
                                                    }else if(index==4){
                                                        viewmodel.signOut()
                                                        navHostController.navigate(Routes.Login.toString()){
                                                            popUpTo(Routes.Home.toString()){inclusive=true}
                                                        }
                                                    }
                                                },
                                                leadingIcon = { Icon(it.image, null) })
                                        }
                                    }
                                }
                            }
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    3 -> {
                        val expandable = remember { mutableStateOf(false) }
                        Column{
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 40.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    " Call",
                                    fontSize = 32.sp, color = colorResource(id = R.color.black),
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {Todo.showToast(context,"Pending")}) {
                                    Icon(
                                        Icons.Default.Search, null,
                                        modifier = Modifier.size(30.dp)
                                    )
                                }
                                Box(contentAlignment = Alignment.Center) {
                                    IconButton(onClick = {
                                        expandable.value = true
                                    }) {
                                        Icon(
                                            Icons.Default.MoreVert, null,
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    DropdownMenu(
                                        expanded = expandable.value,
                                        onDismissRequest = { expandable.value = false }) {
                                        listOf<DropDownData>(
                                            DropDownData(Icons.Default.Settings, "Settings"),
                                            DropDownData(Icons.Default.Info, "Information"),
                                            DropDownData(Icons.Default.Add, "Add Info"),
                                            DropDownData(Icons.Default.Notifications, "Notification"),
                                            DropDownData(Icons.AutoMirrored.Filled.ArrowBack, "Logout")
                                        ).onEachIndexed {index,it->
                                            DropdownMenuItem(
                                                text = { Text(it.name) },
                                                onClick = {
                                                    if(index==0){
                                                        navHostController.navigate(Routes.Setting.toString())
                                                    }else if(index==4){
                                                        viewmodel.signOut()
                                                        navHostController.navigate(Routes.Login.toString()){
                                                            popUpTo(Routes.Home.toString()){inclusive=true}
                                                        }
                                                    }
                                                },
                                                leadingIcon = { Icon(it.image, null) })
                                        }
                                    }
                                }
                            }
                            HorizontalDivider(thickness = 2.dp)
                        }
                    }
                    else -> HorizontalDivider(modifier = Modifier.fillMaxWidth(), thickness = 3.dp)
                }
            }

        }
    ){it->
        NavScreens(Modifier.padding(it),navIndex.intValue,userList,nav)
    }
}
@Composable
fun NavScreens(
    modifier: Modifier,
    index: Int,
    userList: List<UserProfile>,
    nav: (UserProfile) -> Unit
){
    when(index){
        0-> Chats(modifier,userList,nav)
        1-> Update(modifier,userList,nav)
        2-> Communication(modifier,userList)
        3-> Calls(modifier,userList)
    }
}















//                    val expandable = remember { mutableStateOf(false) }
//                    Column(modifier = Modifier.fillMaxWidth()){
//                        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically){
//                            Text("WhatsApp",
//                                fontSize = 32.sp, color = colorResource(id = R.color.light_green),
//                                fontWeight = FontWeight.Bold)
//                            Spacer(modifier = Modifier.weight(1f))
//                            IconButton(onClick = {}){
//                                Icon(painter = painterResource(R.drawable.camera),null,
//                                    modifier = Modifier.size(28.dp))
//                            }
//                            Spacer(modifier = Modifier.width(4.dp))
//                            IconButton(onClick = {}){
//                                Icon(Icons.Default.Search,null,
//                                    modifier = Modifier.size(30.dp))
//                            }
//                            Box(contentAlignment = Alignment.Center){
//                                IconButton(onClick = {
//                                    expandable.value=true
//                                }){
//                                    Icon(Icons.Default.MoreVert,null,
//                                        modifier = Modifier.size(30.dp))
//                                }
//                                DropdownMenu(expanded = expandable.value,
//                                    onDismissRequest = {expandable.value=false}){
//                                    listOf<DropDownData>(
//                                        DropDownData(Icons.Default.Settings,"Settings"),
//                                        DropDownData(Icons.Default.Info,"Information"),
//                                        DropDownData(Icons.Default.Delete,"Delete"),
//                                        DropDownData(Icons.Default.Add,"Add Info"),
//                                        DropDownData(Icons.Default.Notifications,"Notification")
//                                    ).onEach {
//                                        DropdownMenuItem(text = {Text(it.name)},
//                                            onClick = {},
//                                            leadingIcon = {Icon(it.image,null)})
//                                    }
//                                }
//                            }
//                        }
//
//                        Spacer(modifier = Modifier.height(6.dp))
//                        HorizontalDivider(modifier = Modifier.fillMaxWidth(),
//                            thickness = 2.dp)
//                    }