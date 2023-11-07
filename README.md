# Agile-Delivery

## To do
- import et export des livraisons
- meilleur affichage de la carte, des tournées
- traduire le code en français
- faire des tests unitaires

## A améliorer
### Gestion des erreurs :
- fenêtres d'erreurs : améliorer les messages
- à voir pour les 'throw runtimeException' : normalement pas de messages d'erreurs sur la console
- pas de message d'erreur pour file null ( on peut 'Cancel' c'est pas une erreur)
- détecter si le format du xml n'est pas conforme (liste intersection non vide, au moins un entrepot, quoi d'autre ?) : renvoyer une alerte mais afficher quand même la carte si possible
- traiter les intersections isolées
- traiter plus généralement le cas où aucune solution est trouvée pour la tournée dans le calcul

## Bugs
- exeception lors du chargement du pom.xml par exemple (à cause du display dans graphicalView)
