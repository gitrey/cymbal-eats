<!--
  This is the welcome page for the employee UI. It provides links to the main sections of the application.
-->
<template>
  <q-layout view="hhh lpr fff">
    <q-header elevated>
      <Toolbar/>
    </q-header>

    <q-page-container>

      <div class="q-pa-xl q-gutter-md">

        Welcome! From here you can:
        <ul>
          <!-- Link to the kitchen page, where employees can view and manage orders. -->
          <li>
            <router-link to="kitchen">
              View and update pending orders
            </router-link>
          </li>
          <!-- Link to the menu management page, where employees can add, edit, and delete menu items. -->
          <li>
            <router-link to="view-menu">
              Manage menu
            </router-link>
          </li>
        </ul>

      </div>

    </q-page-container>
  </q-layout>
</template>

<script setup>

  import Toolbar from '../components/Toolbar.vue';

</script>
