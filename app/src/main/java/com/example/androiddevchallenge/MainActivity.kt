/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import android.text.Layout
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.androiddevchallenge.data.PuppyModel
import com.example.androiddevchallenge.ui.theme.MyTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyTheme {
                MyApp()
            }
        }
    }
}

// Start building your app here!
@Composable
fun MyApp() {
    Surface(color = MaterialTheme.colors.background) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "puppylist") {
            composable("puppylist") { PuppyList(navController) }
            composable("puppyDetails/{image}/{breed}/{age}/{gender}/{details}",arguments = listOf(
                navArgument("image") { type = NavType.IntType },
                navArgument("breed") { type = NavType.StringType },
                navArgument("age") { type = NavType.StringType },
                navArgument("gender") { type = NavType.StringType },
                navArgument("details") { type = NavType.StringType }
            )

            ) {

                navBackStackEntry ->
                    navBackStackEntry.arguments?.apply {
                        PuppyDetailsCard(
                            getInt("image"),
                            getString("breed"),
                            getString("age"),
                            getString("gender"),
                            getString("details")
                        )
                    }
            }
        }

    }
}

@Composable
fun PuppyList(navController: NavController) {
    Column {
        Text(
            text = "Puppy List", modifier = Modifier.padding(16.dp),
            style = TextStyle(fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
        )
        val puppyList = getPuppysList();
        LazyColumn() {
            items(puppyList) { puppyInfo ->
                PuppyCard(puppyInfo,navController)
            }
        }
    }
}

@Composable
fun PuppyCard(puppyModel: PuppyModel,navController: NavController) {
        Card(elevation = 4.dp,shape = RoundedCornerShape(6),modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clickable(onClick = {navController.navigate(
                        "puppyDetails/${puppyModel.image}/${puppyModel.breedName}/${puppyModel.age}/${puppyModel.gender}/${puppyModel.details}")})
                    .padding(4.dp)


            ) {
        Image(modifier = Modifier.height(200.dp).width(200.dp).padding(4.dp),painter = painterResource(id = puppyModel.image), contentDescription = puppyModel.breedName, contentScale = ContentScale.Crop)
        Column(horizontalAlignment = Alignment.Start,verticalArrangement = Arrangement.Center,modifier = Modifier.padding(4.dp).fillMaxWidth()) {
                Text(style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),text = puppyModel.breedName)
                Text( style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),text = puppyModel.age)
                Text( style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),text = puppyModel.gender)
        }
        }
    }


}

@Composable
fun PuppyDetailsCard( image: Int,
                      breed: String?,
                      age: String?,
                      gender: String?,
                      details: String?,
                      ) {
    Card(elevation = 4.dp,shape = RoundedCornerShape(6),modifier = Modifier.padding(16.dp).fillMaxHeight()) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)


        ) {
            Image(modifier = Modifier.height(200.dp).width(200.dp).padding(4.dp),painter = painterResource(id = image), contentDescription = breed, contentScale = ContentScale.Crop)
            Column(horizontalAlignment = Alignment.Start,verticalArrangement = Arrangement.Center,modifier = Modifier.padding(4.dp).fillMaxWidth()) {
                Text(style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),text = breed.toString())
                Text( style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),text = age.toString())
                Text( style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),text = gender.toString())
                Text( style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 14.sp),text = details.toString())
            }
        }
    }


}

fun getPuppysList(): List<PuppyModel> {
        return listOf(
            PuppyModel(
                "Golden Retriever",
                "2 Months",
                "female",
                "Golden retrievers are very versatile. They’re known as bird dogs, family pets, service dogs for the disabled, and search and rescue dogs.",
                R.drawable.golden_1
            ),
            PuppyModel(
                "Labrador Retriever",
                "45 Days",
                "female",
                "Labrador retrievers are excellent family dogs, as long as you keep in mind their need for exercise and training. These are dogs bred to work and work hard and they love to have jobs to do, particularly retrieving",
                R.drawable.lab_1
            ),
            PuppyModel(
                "Siberian Husky",
                "1 Month",
                "female",
                "Siberian huskies are classic northern dogs. They are intelligent but somewhat independent and stubborn. They thrive on human company, but need firm, gentle training right from puppy hood.",
                R.drawable.sib_1
            ),
            PuppyModel(
                "Rottweiler",
                "30 Days",
                "female",
                "Rottweilers are large, powerful dogs and require extensive socialization and training from early puppyhood.",
                R.drawable.rot_1
            ),
            PuppyModel(
                "German Shepherd",
                "2 Months",
                "female",
                "Smart and easily trained, the ever-popular German shepherd is quite active and likes to have something to do. Therefore, they need ample daily exercise daily; otherwise, they become mischievous or high-strung.",
                R.drawable.sp_1
            ),
            PuppyModel(
                "Golden Retriever",
                "2 Months",
                "male",
                "Golden retrievers are very versatile. They’re known as bird dogs, family pets, service dogs for the disabled, and search and rescue dogs.",
                R.drawable.golden_2
            ),
            PuppyModel(
                "Labrador Retriever",
                "45 Days",
                "male",
                "Labrador retrievers are excellent family dogs, as long as you keep in mind their need for exercise and training. These are dogs bred to work and work hard and they love to have jobs to do, particularly retrieving",
                R.drawable.lab_2
            ),
            PuppyModel(
                "Siberian Husky",
                "1 Month",
                "male",
                "Siberian huskies are classic northern dogs. They are intelligent but somewhat independent and stubborn. They thrive on human company, but need firm, gentle training right from puppy hood.",
                R.drawable.sib_2
            ),
            PuppyModel(
                "Rottweiler",
                "30 Days",
                "male",
                "Rottweilers are large, powerful dogs and require extensive socialization and training from early puppyhood.",
                R.drawable.rot_2
            ),
            PuppyModel(
                "German Shepherd",
                "2 Months",
                "male",
                "Smart and easily trained, the ever-popular German shepherd is quite active and likes to have something to do. Therefore, they need ample daily exercise daily; otherwise, they become mischievous or high-strung.",
                R.drawable.sp_2
            )
        )
    }

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    MyTheme {
        MyApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
        MyApp()
    }


}
